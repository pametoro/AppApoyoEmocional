package com.example.appapoyoemocional.viewModel

import com.example.appapoyoemocional.data.modelo.Post
import com.example.appapoyoemocional.repository.PostRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest : BehaviorSpec({

    val testDispatcher = StandardTestDispatcher()

    // Mock del repositorio
    val mockRepository = mockk<PostRepository>()

    lateinit var viewModel: PostViewModel

    // Configuración del Test Dispatcher para reemplazar Dispatchers.Main
    beforeSpec {
        Dispatchers.setMain(testDispatcher)
    }

    afterSpec {
        Dispatchers.resetMain()
    }

    // Datos de prueba
    val mockPosts = listOf(
        Post(userId = 1, id = 1, title = "Título 1", body = "Contenido 1"),
        Post(userId = 1, id = 2, title = "Título 2", body = "Contenido 2")
    )

    // --- ESCENARIO 1: CARGA EXITOSA ---

    Given("El ViewModel se inicializa con una carga de posts exitosa") {

        // Setup: El repositorio devuelve los posts de prueba con éxito
        coEvery { mockRepository.getPosts() } returns mockPosts

        // Inicialización: Inyectando el dispatcher de prueba
        viewModel = PostViewModel(repository = mockRepository, dispatcher = testDispatcher)

        // CLAVE: Ejecutar la corrutina del bloque init (fetchPosts)
        runTest(testDispatcher) {
            testDispatcher.scheduler.advanceUntilIdle()

            When("La inicialización se completa") {
                Then("La lista de posts debe contener los datos devueltos por el repositorio") {
                    viewModel.postList.value shouldBe mockPosts
                }
            }
        }
    }

    // --- ESCENARIO 2: CARGA FALLIDA ---

    Given("El ViewModel se inicializa con una carga de posts fallida") {

        val expectedException = Exception("Error de red simulado")

        // Setup: El repositorio lanza una excepción (simulando un fallo de red/DB)
        coEvery { mockRepository.getPosts() } throws expectedException

        // Inicialización: Inyectando el dispatcher de prueba
        viewModel = PostViewModel(repository = mockRepository, dispatcher = testDispatcher)

        // CLAVE: Ejecutar la corrutina del bloque init (fetchPosts)
        runTest(testDispatcher) {
            // Nota: advanceUntilIdle permite que la corrutina falle y termine.
            testDispatcher.scheduler.advanceUntilIdle()

            When("La inicialización falla") {
                Then("La lista de posts debe seguir vacía") {
                    viewModel.postList.value shouldBe emptyList()
                }
            }
        }
    }
})