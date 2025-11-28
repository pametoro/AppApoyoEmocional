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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class PerfilViewTest : BehaviorSpec({

    val testDispatcher = StandardTestDispatcher()

    // Mock del repositorio
    val mockRepositorio = mockk<PerfilRepositorio>()

    lateinit var viewModel: PerfilViewModel

    // CORRECCIÓN: Se elimina @get:Rule y se usa el setup de Corrutinas:
    beforeSpec {
        Dispatchers.setMain(testDispatcher)
    }

    afterSpec {
        Dispatchers.resetMain()
    }

    // --- ESCENARIO DE INICIALIZACIÓN ---

    Given("El ViewModel con un repositorio configurado") {
        val uriVacia = PerfilDeUsuario(id = 1, nombre = "Test", imagenUri = null)
        val nuevaMockUri = mockk<Uri>()

        // 1. Configurar el comportamiento del mock para el constructor
        coEvery { mockRepositorio.getProfile() } returns uriVacia

        coEvery { mockRepositorio.updateImage(any()) } returns Unit

        // 2. Inicializar el ViewModel, inyectando el mock
        viewModel = PerfilViewModel(repositorio = mockRepositorio, dispatcher = testDispatcher)

        runTest(testDispatcher) {
            testDispatcher.scheduler.advanceUntilIdle()
        }

        When("Se llama a setImage con la nueva Uri") {
            viewModel.setImage(nuevaMockUri) // Inicia la corrutina de actualización

            // Avanzamos el dispatcher para que la corrutina termine
            runTest(testDispatcher) {
                // CLAVE: Forzar la finalización de la corrutina iniciada por setImage()
                testDispatcher.scheduler.advanceUntilIdle()

                Then("1. El StateFlow imagenUri debe actualizarse a la nueva Uri") {
                    // Línea 51 (la que fallaba) ahora tiene el valor actualizado
                    viewModel.imagenUri.value shouldBe nuevaMockUri
                }

                Then("2. Se debe llamar al método updateImage del repositorio con la Uri correcta") {
                    coVerify(exactly = 1) { mockRepositorio.updateImage(nuevaMockUri) }
                }
            }
        }
    }

    // --- ESCENARIO DE ACTUALIZACIÓN (EXITO) ---

    Given("El ViewModel con un repositorio configurado") {
        val uriVacia = PerfilDeUsuario(id = 1, nombre = "Test", imagenUri = null)
        val nuevaMockUri = mockk<Uri>()

        // Setup: Repositorio devuelve null inicialmente
        coEvery { mockRepositorio.getProfile() } returns uriVacia

        // Setup: Mockea la función suspendida updateImage.
        // Usamos 'any()' para simular la llamada sin importar la Uri específica.
        coEvery { mockRepositorio.updateImage(any()) } returns Unit

        viewModel = PerfilViewModel(repositorio = mockRepositorio)

        When("Se llama a setImage con la nueva Uri") {
            viewModel.setImage(nuevaMockUri)

            // Avanzamos el dispatcher para que la corrutina termine
            runTest(testDispatcher) {

                Then("1. El StateFlow imagenUri debe actualizarse a la nueva Uri") {
                    viewModel.imagenUri.value shouldBe nuevaMockUri
                }

                Then("2. Se debe llamar al método updateImage del repositorio con la Uri correcta") {
                    // Verificamos que se llamó al repositorio con la nueva URI
                    coVerify(exactly = 1) { mockRepositorio.updateImage(nuevaMockUri) }
                }
            }
        }
    }

    // --- ESCENARIO DE LIMPIEZA (URI NULA) ---

    Given("El ViewModel con una imagen previamente establecida") {
        // Setup de inicialización
        coEvery { mockRepositorio.getProfile() } returns PerfilDeUsuario(
            id = 1,
            nombre = "Test",
            imagenUri = mockk<Uri>()
        )

        // Setup de actualización (limpieza a null)
        coEvery { mockRepositorio.updateImage(null) } returns Unit

        viewModel = PerfilViewModel(repositorio = mockRepositorio)

        When("Se llama a setImage con null") {
            viewModel.setImage(null)

            runTest(testDispatcher) {
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