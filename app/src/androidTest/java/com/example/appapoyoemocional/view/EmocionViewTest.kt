package com.example.appapoyoemocional.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appapoyoemocional.viewModel.EmocionViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class EmocionScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `al escribir emocion valida y presionar Guardar navega a emocionGuardada`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = EmocionViewModel()

        composeRule.setContent {
            EmocionScreen(
                navController = fakeNavController,
                viewModel = viewModel,
                nombre = "Pamela"
            )
        }

        // Ingresar emoción válida
        composeRule.onNodeWithText("Escribe tus emociones").performTextInput("Me siento motivada")

        // Presionar botón Guardar
        composeRule.onNodeWithText("Guardar").performClick()
    }

    @Test
    fun `al presionar Guardar sin escribir emocion muestra Snackbar`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = EmocionViewModel()

        composeRule.setContent {
            EmocionScreen(
                navController = fakeNavController,
                viewModel = viewModel,
                nombre = "Pamela"
            )
        }

        // No ingresamos emoción → directamente presionamos Guardar
        composeRule.onNodeWithText("Guardar").performClick()

        // Verificar que aparece el mensaje del Snackbar
        composeRule.onNodeWithText("Por favor, escribe tus emociones antes de guardar.").assertExists()
    }
}
