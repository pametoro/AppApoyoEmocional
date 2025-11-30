package com.example.appapoyoemocional.viewModel

import androidx.camera.core.ImageProxy
import com.example.appapoyoemocional.data.modelo.RecFacialModel
import com.google.mlkit.vision.face.Face
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class RecFacialViewModelTest : BehaviorSpec({

    val testDispatcher = StandardTestDispatcher()

    // Mocks de dependencias externas
    val mockImageProxy = mockk<ImageProxy>() // Usamos ImageProxy como argumento de entrada
    val mockFace1 = mockk<Face>()

    // Slots para capturar los callbacks de éxito y error
    val successSlot = slot<(List<Face>) -> Unit>()
    val errorSlot = slot<() -> Unit>() // El callback de error no lleva argumento (ver RecFacialModel.kt)

    // Configuración de Corrutinas
    beforeSpec {
        Dispatchers.setMain(testDispatcher)
    }

    afterSpec {
        Dispatchers.resetMain()
    }

    // --- ESCENARIO 1: DETECCIÓN EXITOSA DE ROSTROS ---

    Given("El ViewModel y una imagen que resulta en detección exitosa") {
        val viewModel = RecFacialViewModel()
        val expectedFaces = listOf(mockFace1)

        mockkObject(RecFacialModel)

        // Simular que detectFaces tiene éxito e invoca el callback 'onResult'
        every {
            RecFacialModel.detectFaces(
                imageProxy = any(), // Capturamos el ImageProxy (aunque sea mock)
                onResult = capture(successSlot), // Capturamos la función onResult
                onError = any()
            )
        } answers {
            // Ejecutamos el callback capturado con los resultados simulados
            successSlot.captured.invoke(expectedFaces)
        }

        When("Se llama a la función que inicia el análisis") {

            runTest(testDispatcher) {


                Then("El método detectFaces debe haber sido llamado") {
                    // Verificamos que el modelo fue invocado
                    verify(exactly = 1) { RecFacialModel.detectFaces(any(), any(), any()) }
                }

                Then("El StateFlow 'faces' debe actualizarse con la lista de rostros detectados") {
                }

                Then("El StateFlow 'error' debe ser nulo") {
                    viewModel.error.value shouldBe null
                }
            }
        }

        unmockkObject(RecFacialModel)
    }

    Given("El ViewModel y una imagen que resulta en un error de detección") {
        val viewModel = RecFacialViewModel()
        val defaultEmotion = "Neutral" // Valor por defecto

        mockkObject(RecFacialModel)

        every {
            RecFacialModel.detectFaces(
                imageProxy = any(),
                onResult = any(),
                onError = capture(errorSlot) // Capturamos la función onError
            )
        } answers {
            // Ejecutamos el callback 'onError' sin argumento (ya que la firma es () -> Unit)
            errorSlot.captured.invoke()
        }

        When("Se llama a la función que inicia el análisis") {
            // viewModel.initAnalysis(mockImageProxy) // Llamada al método real

            runTest(testDispatcher) {
                Then("El método detectFaces debe haber sido llamado") {
                    verify(exactly = 1) { RecFacialModel.detectFaces(any(), any(), any()) }
                }

                Then("El StateFlow de emoción debe mantener el valor por defecto o establecer un estado de error") {

                }
            }
        }

        unmockkObject(RecFacialModel)
    }
})