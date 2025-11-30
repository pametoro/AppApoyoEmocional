package com.example.appapoyoemocional.viewModel

import android.net.Uri
import com.example.appapoyoemocional.data.modelo.PerfilDeUsuario
import com.example.appapoyoemocional.repository.PerfilRepositorio
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
class PerfilViewModelTest2 : BehaviorSpec({

    val testDispatcher = StandardTestDispatcher()
    val mockRepositorio = mockk<PerfilRepositorio>()
    lateinit var viewModel: PerfilViewModel

    beforeSpec {
        Dispatchers.setMain(testDispatcher)
    }

    afterSpec {
        Dispatchers.resetMain()
    }

    // --- ESCENARIO 1: ACTUALIZACIÓN DE IMAGEN ---

    Given("El ViewModel con un repositorio y un perfil inicial vacío") {
        val uriVacia = PerfilDeUsuario(id = 1, nombre = "Test", imagenUri = null)
        val nuevaMockUri = mockk<Uri>()

        coEvery { mockRepositorio.getProfile() } returns uriVacia
        coEvery { mockRepositorio.updateImage(any()) } returns Unit

        viewModel = PerfilViewModel(repositorio = mockRepositorio, dispatcher = testDispatcher)

        // Ejecutar el bloque init inicial
        runTest(testDispatcher) { testDispatcher.scheduler.advanceUntilIdle() }

        When("Se llama a setImage con la nueva Uri") {
            viewModel.setImage(nuevaMockUri)

            runTest(testDispatcher) {
                testDispatcher.scheduler.advanceUntilIdle() // Espera que setImage() termine

                Then("1. El StateFlow imagenUri debe actualizarse a la nueva Uri") {
                    viewModel.imagenUri.value shouldBe nuevaMockUri
                }

                Then("2. Se debe llamar al método updateImage del repositorio") {
                    coVerify(exactly = 1) { mockRepositorio.updateImage(nuevaMockUri) }
                }
            }
        }
    }

    // --- ESCENARIO 2: LIMPIEZA DE IMAGEN (SET NULO) ---

    Given("El ViewModel con una imagen previamente establecida") {
        val uriPrevia = mockk<Uri>()
        coEvery { mockRepositorio.getProfile() } returns PerfilDeUsuario(
            id = 1,
            nombre = "Test",
            imagenUri = uriPrevia
        )

        coEvery { mockRepositorio.updateImage(null) } returns Unit

        viewModel = PerfilViewModel(repositorio = mockRepositorio, dispatcher = testDispatcher)

        // Ejecutar el bloque init para cargar la URI previa
        runTest(testDispatcher) { testDispatcher.scheduler.advanceUntilIdle() }


        When("Se llama a setImage con null") {
            viewModel.setImage(null)

            runTest(testDispatcher) {
                testDispatcher.scheduler.advanceUntilIdle()

                Then("1. El StateFlow imagenUri debe actualizarse a null") {
                    viewModel.imagenUri.value shouldBe null
                }

                Then("2. Se debe llamar al repositorio con null para guardar el cambio") {
                    coVerify { mockRepositorio.updateImage(null) }
                }
            }
        }
    }
})