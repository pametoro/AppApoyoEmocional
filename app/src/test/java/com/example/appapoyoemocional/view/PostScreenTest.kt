package com.example.appapoyoemocional.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.appapoyoemocional.data.modelo.Post
import com.example.appapoyoemocional.view.screen.PostScreen
import com.example.appapoyoemocional.viewModel.PostViewModel
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class PostScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `pantalla muestra titulo y posts`() {
        val fakeNavController = mockk<NavController>(relaxed = true)

        // ViewModel con datos falsos
        val postsFalsos = listOf(
            Post(userId = 1, id = 1, title = "Primer post", body = "Contenido de prueba"),
            Post(userId = 2, id = 2, title = "Segundo post", body = "Más contenido")
        )
        val viewModel = PostViewModel().apply {
            // Forzamos el flujo con datos falsos
            val flujo = MutableStateFlow(postsFalsos)
            this::class.java.getDeclaredField("_postList").apply {
                isAccessible = true
                set(this@apply, flujo)
            }
        }

        composeRule.setContent {
            PostScreen(viewModel = viewModel, navController = fakeNavController)
        }

        // Verificar título
        composeRule.onNodeWithText("Listado de Posts").assertExists()

        // Verificar posts
        composeRule.onNodeWithText("Título: Primer post").assertExists()
        composeRule.onNodeWithText("Contenido de prueba").assertExists()
        composeRule.onNodeWithText("Título: Segundo post").assertExists()
        composeRule.onNodeWithText("Más contenido").assertExists()
    }

    @Test
    fun `clic en boton retroceso llama a popBackStack`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = PostViewModel()

        composeRule.setContent {
            PostScreen(viewModel = viewModel, navController = fakeNavController)
        }

        composeRule.onNodeWithText("<").performClick()

        verify { fakeNavController.popBackStack() }
    }

    @Test
    fun `clic en boton perfil navega a perfil Invitado`() {
        val fakeNavController = mockk<NavController>(relaxed = true)
        val viewModel = PostViewModel()

        composeRule.setContent {
            PostScreen(viewModel = viewModel, navController = fakeNavController)
        }

        composeRule.onNodeWithText(">").performClick()

        verify { fakeNavController.navigate("perfil/Invitado") }
    }
}
