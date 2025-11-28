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
class PerfilViewModelTest : BehaviorSpec({

    // Instancia de nuestro dispatcher de prueba
    val testDispatcher = StandardTestDispatcher()

    // Mock del repositorio
    val mockRepositorio = mockk<PerfilRepositorio>()

    lateinit var viewModel: PerfilViewModel

    // --- Configuración de Corrutinas (Gestiona Dispatchers.Main) ---
    beforeSpec {
        Dispatchers.setMain(testDispatcher)
    }

    afterSpec {
        Dispatchers.resetMain()
    }
    // -----------------------------------------------------------------

    // --- ESCENARIO 1: INICIALIZACIÓN ---
    Given("Un PerfilViewModel inicializado") {

        val uriInicial = mockk<Uri>()
        val perfilConUri = PerfilDeUsuario(id = 1, nombre = "Test", imagenUri = uriInicial)

        // Configurar el mock para que devuelva un perfil con URI.
        coEvery { mockRepositorio.getProfile() } returns perfilConUri

        // Inicializar el ViewModel, inyectando el dispatcher (CLAVE para el testing).
        viewModel = PerfilViewModel(repositorio = mockRepositorio, dispatcher = testDispatcher)

        // Hacemos que el dispatcher ejecute el bloque 'init' del ViewModel.
        runTest(testDispatcher) {
            // Esto garantiza que la corrutina de inicialización termine.
            testDispatcher.scheduler.advanceUntilIdle()

            When("Se inicializa el ViewModel") {
                Then("El StateFlow imagenUri debe inicializarse con la Uri del repositorio") {
                    viewModel.imagenUri.value shouldBe uriInicial
                }
            }
        }
    }

    // --- ESCENARIO 2: ACTUALIZACIÓN (SET IMAGE) ---
    Given("Un PerfilViewModel listo para actualizar la imagen") {

        val perfilInicial = PerfilDeUsuario(id = 1, nombre = "Usuario", imagenUri = null)
        val nuevaUri = mockk<Uri>(relaxed = true)

        // Setup: Repositorio devuelve un perfil vacío inicialmente
        coEvery { mockRepositorio.getProfile() } returns perfilInicial

        // Setup: Mockea la función updateImage
        coEvery { mockRepositorio.updateImage(any()) } returns Unit

        // Inicialización con el dispatcher inyectado
        viewModel = PerfilViewModel(repositorio = mockRepositorio, dispatcher = testDispatcher)

        // Hacemos que el dispatcher ejecute el bloque 'init' del ViewModel
        // (En este caso, se inicializará a null)
        runTest(testDispatcher) {
            testDispatcher.scheduler.advanceUntilIdle()
        }

        When("Se llama a setImage con una nueva Uri") {
            viewModel.setImage(nuevaUri) // Inicia la corrutina asíncrona

            // runTest y advanceUntilIdle() para resolver el error de asincronía
            runTest(testDispatcher) {
                // CLAVE DE LA SOLUCIÓN: Ejecuta la corrutina pendiente en viewModel.setImage()
                testDispatcher.scheduler.advanceUntilIdle()

                Then("El StateFlow 'imagenUri' debe actualizarse a la nueva Uri") {
                    // Esta aserción ahora espera el valor correcto
                    viewModel.imagenUri.value shouldBe nuevaUri
                }

                Then("Se debe llamar al método updateImage del repositorio con la nueva Uri") {
                    coVerify(exactly = 1) { mockRepositorio.updateImage(nuevaUri) }
                }
            }
        }
    }
})