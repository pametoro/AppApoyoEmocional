package com.example.appapoyoemocional.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appapoyoemocional.viewModel.UsuarioViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FormularioScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `al llenar el formulario y presionar Registrar navega a resumen`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = UsuarioViewModel()

        composeRule.setContent {
            FormularioScreen(navController = fakeNavController, viewModel = viewModel)
        }

        // Ingresar datos válidos
        composeRule.onNodeWithText("Nombre").performTextInput("Pamela")
        composeRule.onNodeWithText("Correo").performTextInput("pamela@correo.com")
        composeRule.onNodeWithText("Clave").performTextInput("12345678")
        composeRule.onNodeWithText("Dirección").performTextInput("Santiago")

        // Aceptar términos
        composeRule.onNodeWithText("Acepto los términos y condiciones").performClick()

        // Presionar botón Registrar
        composeRule.onNodeWithText("Registrar").performClick()

        // Verificar navegación
        verify { fakeNavController.navigate("resumen") }
    }
}