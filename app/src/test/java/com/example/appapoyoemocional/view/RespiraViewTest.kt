package com.example.appapoyoemocional.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.appapoyoemocional.viewModel.RespiraViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class RespiraScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `pantalla muestra titulo, imagen y texto`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = RespiraViewModel()

        composeRule.setContent {
            RespiraScreen(navController = fakeNavController, viewModel = viewModel)
        }

        // Verificar título
        composeRule.onNodeWithText("Ejercicio de respiración").assertExists()

        // Verificar imagen con contentDescription
        composeRule.onNodeWithContentDescription("persona haciendo yoga").assertExists()

        // Verificar texto de instrucciones
        composeRule.onNodeWithText("Respira profundamente y sigue las instrucciones del video").assertExists()
    }

    @Test
    fun `clic en boton retroceso llama a popBackStack`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = RespiraViewModel()

        composeRule.setContent {
            RespiraScreen(navController = fakeNavController, viewModel = viewModel)
        }

        composeRule.onNodeWithText("<").performClick()

        verify { fakeNavController.popBackStack() }
    }

    @Test
    fun `clic en Cerrar sesion navega a inicio`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = RespiraViewModel()

        composeRule.setContent {
            RespiraScreen(navController = fakeNavController, viewModel = viewModel)
        }

        composeRule.onNodeWithText("Cerrar sesión").performClick()

        verify { fakeNavController.navigate("inicio") }
    }
}
