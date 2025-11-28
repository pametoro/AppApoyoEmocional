package com.example.appapoyoemocional.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appapoyoemocional.viewModel.InicioViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PaginaInicioTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `pantalla muestra descripcion y botones cuando mostrarBoton es true`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = InicioViewModel().apply {
            actualizarDescripcion("Bienvenido a la app")
            // mostrarBoton por defecto es true en tu UIState
        }

        composeRule.setContent {
            PaginaInicio(navController = fakeNavController, viewModel = viewModel)
        }

        // Verificar que la descripción aparece
        composeRule.onNodeWithText("Bienvenido a la app").assertExists()

        // Verificar que los botones existen
        composeRule.onNodeWithText("Registrate").assertExists()
        composeRule.onNodeWithText("Ir al Perfil").assertExists()
        composeRule.onNodeWithText("Iniciar reconocimiento facial").assertExists()

        // Simular clic en cada botón y verificar navegación
        composeRule.onNodeWithText("Registrate").performClick()
        verify { fakeNavController.navigate("FormularioScreen") }

        composeRule.onNodeWithText("Ir al Perfil").performClick()
        verify { fakeNavController.navigate("perfil/Invitado") }

        composeRule.onNodeWithText("Iniciar reconocimiento facial").performClick()
        verify { fakeNavController.navigate("reconocimiento") }
    }

    @Test
    fun `pantalla oculta botones de registro y perfil cuando mostrarBoton es false`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = InicioViewModel().apply {
            ocultarBoton()
            actualizarDescripcion("Bienvenido a la app")
        }

        composeRule.setContent {
            PaginaInicio(navController = fakeNavController, viewModel = viewModel)
        }

        // Verificar que los botones de registro y perfil NO aparecen
        composeRule.onNodeWithText("Registrate").assertDoesNotExist()
        composeRule.onNodeWithText("Ir al Perfil").assertDoesNotExist()

        // El botón de reconocimiento facial siempre debe estar
        composeRule.onNodeWithText("Iniciar reconocimiento facial").assertExists()
    }
}