package com.example.appapoyoemocional.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appapoyoemocional.data.modelo.Post
import com.example.appapoyoemocional.repository.PostRepository
import com.example.appapoyoemocional.view.screen.PostScreen
import com.example.appapoyoemocional.viewModel.PostViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
open class FakePostRepository(private val posts: List<Post>) : PostRepository() {
    override suspend fun getPosts(): List<Post> {
        return posts
    }
}

class PostScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `pantalla muestra titulo y posts`() {
        val fakeNavController = mockk<NavController>(relaxed = true)

        // 1. Definir los datos falsos
        val postsFalsos = listOf(
            Post(userId = 1, id = 1, title = "Primer post", body = "Contenido de prueba"),
            Post(userId = 2, id = 2, title = "Segundo post", body = "Más contenido")
        )

        // 2. Crear el Repositorio Falso con los datos
        val fakeRepository = FakePostRepository(postsFalsos)

        // 3. Crear el ViewModel, inyectando el Repositorio Falso (¡CORRECCIÓN!)
        // Asumiendo que PostViewModel ahora acepta un PostRepository en su constructor.
        val viewModel = PostViewModel(repository = fakeRepository)

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

        // Usamos un repositorio vacío para mantener la consistencia
        val viewModel = PostViewModel(repository = FakePostRepository(emptyList()))

        composeRule.setContent {
            PostScreen(viewModel = viewModel, navController = fakeNavController)
        }

        // Asumiendo que el botón de retroceso contiene el texto "<"
        composeRule.onNodeWithText("<").performClick()

        verify { fakeNavController.popBackStack() }
    }

    @Test
    fun `clic en boton perfil navega a perfil Invitado`() {
        val fakeNavController = mockk<NavController>(relaxed = true)

        // Usamos un repositorio vacío para mantener la consistencia
        val viewModel = PostViewModel(repository = FakePostRepository(emptyList()))

        composeRule.setContent {
            PostScreen(viewModel = viewModel, navController = fakeNavController)
        }

        // Asumiendo que el botón de perfil contiene el texto ">"
        composeRule.onNodeWithText(">").performClick()

        verify { fakeNavController.navigate("perfil/Invitado") }
    }
}