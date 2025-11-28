package com.example.appapoyoemocional.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.appapoyoemocional.viewModel.UsuarioViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class ResumenScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `pantalla muestra datos ingresados`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = UsuarioViewModel().apply {
            onNombreChange("Pamela")
            onCorreoChange("pamela@correo.com")
            onClaveChange("12345678")
            onDireccionChange("Santiago")
            onAceptarTerminosChange(true)
        }

        composeRule.setContent {
            ResumenScreen(navController = fakeNavController, viewModel = viewModel, nombreUsuario = "Pamela")
        }

        // Verificar textos
        composeRule.onNodeWithText("Datos ingresados con exito").assertExists()
        composeRule.onNodeWithText("Nombre: Pamela").assertExists()
        composeRule.onNodeWithText("Correo: pamela@correo.com").assertExists()
        composeRule.onNodeWithText("Dirección: Santiago").assertExists()
        composeRule.onNodeWithText("Contraseña: ********").assertExists()
        composeRule.onNodeWithText("¿Términos Aceptados?: Aceptados").assertExists()
    }

    @Test
    fun `clic en Volver llama a popBackStack`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = UsuarioViewModel()

        composeRule.setContent {
            ResumenScreen(navController = fakeNavController, viewModel = viewModel, nombreUsuario = "Pamela")
        }

        composeRule.onNodeWithText("Volver").performClick()

        verify { fakeNavController.popBackStack() }
    }

    @Test
    fun `clic en Ver Posts navega a posts`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = UsuarioViewModel()

        composeRule.setContent {
            ResumenScreen(navController = fakeNavController, viewModel = viewModel, nombreUsuario = "Pamela")
        }

        composeRule.onNodeWithText("Ver Posts").performClick()

        verify { fakeNavController.navigate("posts") }
    }

    @Test
    fun `clic en Ir al Perfil navega a perfil Invitado`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = UsuarioViewModel()

        composeRule.setContent {
            ResumenScreen(navController = fakeNavController, viewModel = viewModel, nombreUsuario = "Pamela")
        }

        composeRule.onNodeWithText("Ir al Perfil").performClick()

        verify { fakeNavController.navigate("perfil/Invitado") }
    }
}
