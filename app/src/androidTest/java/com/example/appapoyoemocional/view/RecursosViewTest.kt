package com.example.appapoyoemocional.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appapoyoemocional.viewModel.RecursosViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecursosScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `pantalla muestra titulo y consejos`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = RecursosViewModel()

        composeRule.setContent {
            RecursosScreen(navController = fakeNavController, viewModel = viewModel)
        }

        // Verificar que el título aparece
        composeRule.onNodeWithText("Consejos para Sentirte Mejor").assertExists()

        // Verificar que los consejos iniciales aparecen
        composeRule.onNodeWithText("Respira profundo y exhala lentamente.").assertExists()
        composeRule.onNodeWithText("Escribe lo que sientes sin juzgarte.").assertExists()
        composeRule.onNodeWithText("Escucha tu canción favorita.").assertExists()
        composeRule.onNodeWithText("Sal a caminar unos minutos.").assertExists()
    }

    @Test
    fun `clic en boton retroceso llama a popBackStack`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = RecursosViewModel()

        composeRule.setContent {
            RecursosScreen(navController = fakeNavController, viewModel = viewModel)
        }

        // Simular clic en el botón "<"
        composeRule.onNodeWithText("<").performClick()

        // Verificar que se llamó a popBackStack
        verify { fakeNavController.popBackStack() }
    }

    @Test
    fun `clic en Ver ejercicio de respiracion navega a Respira`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = RecursosViewModel()

        composeRule.setContent {
            RecursosScreen(navController = fakeNavController, viewModel = viewModel)
        }

        // Simular clic en el botón "Ver ejercicio de respiración"
        composeRule.onNodeWithText("Ver ejercicio de respiración").performClick()

        // Verificar que se llamó a navigate("Respira")
        verify { fakeNavController.navigate("Respira") }
    }
}
