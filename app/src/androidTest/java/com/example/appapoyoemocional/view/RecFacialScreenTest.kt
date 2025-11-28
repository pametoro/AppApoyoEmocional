package com.example.appapoyoemocional.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appapoyoemocional.viewModel.RecFacialViewModel
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class RecFacialScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `pantalla muestra textos y botones`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = RecFacialViewModel()

        composeRule.setContent {
            RecFacialScreen(navController = fakeNavController, viewModel = viewModel)
        }

        // Verificar textos principales
        composeRule.onNodeWithText("Rostros detectados: 0").assertExists()
        composeRule.onNodeWithText("Alinea tu rostro dentro del círculo para iniciar el reconocimiento").assertExists()
        composeRule.onNodeWithText("Detectar rostro").assertExists()
        composeRule.onNodeWithText("<").assertExists()
    }

    @Test
    fun `clic en boton retroceso llama a popBackStack`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = RecFacialViewModel()

        composeRule.setContent {
            RecFacialScreen(navController = fakeNavController, viewModel = viewModel)
        }

        composeRule.onNodeWithText("<").performClick()

        verify { fakeNavController.popBackStack() }
    }

    @Test
    fun `clic en Detectar rostro llama a processImage`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = spyk(RecFacialViewModel(), recordPrivateCalls = true)

        composeRule.setContent {
            RecFacialScreen(navController = fakeNavController, viewModel = viewModel)
        }

        composeRule.onNodeWithText("Detectar rostro").performClick()

        // Verificar que se llamó a processImage
        verify { viewModel.processImage(any()) }
    }
}