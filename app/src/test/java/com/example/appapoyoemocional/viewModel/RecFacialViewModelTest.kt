package com.example.appapoyoemocional.viewModel

import com.example.appapoyoemocional.data.modelo.RecFacialModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import io.mockk.impl.annotations.MockK
import io.mockk.coEvery // Mantenemos coEvery para la sintaxis de captura de lambda, aunque la llamada es 'every'
import io.mockk.invoke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class RecFacialViewModelTest : BehaviorSpec({

    val testDispatcher = StandardTestDispatcher()

    // Mocks de dependencias externas (ML Kit)
    val mockInputImage = mockk<InputImage>()
    val mockFace1 = mockk<Face>()

    // Configuración de Corrutinas
    beforeSpec {
        Dispatchers.setMain(testDispatcher)
    }

    afterSpec {
        Dispatchers.resetMain()
    }

    // El beforeEach fue eliminado para mover la inicialización a cada Given.

    // --- ESCENARIO 1: DETECCIÓN EXITOSA DE ROSTROS ---

    Given("El ViewModel y una imagen que resulta en detección exitosa") {

        // CORRECCIÓN CLAVE: Inicializar el ViewModel aquí (soluciona UninitializedPropertyAccessException)
        val viewModel = RecFacialViewModel()

        val expectedFaces = listOf(mockFace1)

        // Habilitar mocking para la clase estática/Object RecFacialModel
        mockkObject(RecFacialModel)

        // Definir el comportamiento del mock (Simular éxito y capturar el callback)
        every { // Usar 'every' ya que detectFaces no es suspend
            RecFacialModel.detectFaces(
                image = any(),
                onResult = captureLambda(), // Capturamos la función onResult
                onError = any()
            )
        } answers {
            // Ejecutamos el callback 'onResult' con los rostros simulados
            lambda<(List<Face>) -> Unit>().invoke(expectedFaces)
        }

        When("Se llama a processImage(image)") {
            viewModel.processImage(mockInputImage)

            runTest(testDispatcher) {
                Then("El StateFlow 'faces' debe actualizarse con la lista de rostros detectados") {
                    viewModel.faces.value shouldBe expectedFaces
                    viewModel.error.value shouldBe null
                }

                Then("El método detectFaces debe haber sido llamado") {
                    verify(exactly = 1) { RecFacialModel.detectFaces(any(), any(), any()) }
                }
            }
        }

        // Limpiar el mocking estático después del Given
        unmockkObject(RecFacialModel)
    }

    // --- ESCENARIO 2: DETECCIÓN FALLIDA (ERROR) ---

    Given("El ViewModel y una imagen que resulta en un error de detección") {

        // CORRECCIÓN CLAVE: Inicializar el ViewModel aquí
        val viewModel = RecFacialViewModel()

        val errorMessage = "Fallo de inicialización de cámara"
        val mockException = Exception(errorMessage)

        mockkObject(RecFacialModel)

        every { // Usar 'every' ya que detectFaces no es suspend
            RecFacialModel.detectFaces(
                image = any(),
                onResult = any(),
                onError = captureLambda() // Capturamos la función onError
            )
        } answers {
            // Ejecutamos el callback 'onError' con la excepción simulada
            lambda<(Exception) -> Unit>().invoke(mockException)
        }

        When("Se llama a processImage(image)") {
            viewModel.processImage(mockInputImage)

            runTest(testDispatcher) {
                Then("El StateFlow 'error' debe actualizarse con el mensaje de la excepción") {
                    viewModel.faces.value shouldBe emptyList()
                    viewModel.error.value shouldBe errorMessage
                }

                verify(exactly = 1) { RecFacialModel.detectFaces(any(), any(), any()) }
            }
        }

        unmockkObject(RecFacialModel)
    }
})