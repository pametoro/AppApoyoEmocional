package com.example.appapoyoemocional

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.appapoyoemocional.view.EmocionGuardadaScreen
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class EmocionGuardadaScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `pantalla muestra datos y boton vuelve`() {
        val fakeNavController = mockk<NavController>(relaxed = true)

        composeRule.setContent {
            EmocionGuardadaScreen(
                navController = fakeNavController,
                nombreUsuario = "Pamela",
                emocionTexto = "Me siento motivada"
            )
        }

        // Verificar que los textos aparecen
        composeRule.onNodeWithText("¡Emoción guardada con éxito!").assertExists()
        composeRule.onNodeWithText("Hola, Pamela").assertExists()
        composeRule.onNodeWithText("Usuario dijo:").assertExists()
        composeRule.onNodeWithText("Me siento motivada").assertExists()

        // Simular clic en el botón
        composeRule.onNodeWithText("Volver a la reflexión").performClick()

        // Verificar que se llamó a popBackStack
        verify { fakeNavController.popBackStack() }
    }
}