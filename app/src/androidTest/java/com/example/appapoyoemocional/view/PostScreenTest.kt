package com.example.appapoyoemocional.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.appapoyoemocional.data.modelo.Post
import com.example.appapoyoemocional.repository.PostRepository // Asegúrate de que esta interfaz exista
import com.example.appapoyoemocional.view.screen.PostScreen
import com.example.appapoyoemocional.viewModel.PostViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4 // Mantenido para asegurar la ejecución


class FakePostRepository(private val posts: List<Post>) : PostRepository() {
    override suspend fun getPosts(): List<Post> {
        return posts
    }
}

@RunWith(AndroidJUnit4::class)
class PostScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `pantalla_muestra_titulo_y_posts`() {
        val fakeNavController = mockk<NavController>(relaxed = true)

        // 1. Definir los datos falsos
        val postsFalsos = listOf(
            Post(userId = 1, id = 1, title = "Primer post", body = "Contenido de prueba"),
            Post(userId = 2, id = 2, title = "Segundo post", body = "Más contenido")
        )

        // 2. Crear el Repositorio Falso con los datos
        val fakeRepository = FakePostRepository(postsFalsos)

        // 3. Crear el ViewModel, inyectando el Repositorio Falso
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
    fun `clic_en_boton_retroceso_llama_a_popBackStack`() { // Renombrado
        val fakeNavController = mockk<NavController>(relaxed = true)

        // Usamos un repositorio vacío para mantener la consistencia
        val viewModel = PostViewModel(repository = FakePostRepository(emptyList()))

        composeRule.setContent {
            PostScreen(viewModel = viewModel, navController = fakeNavController)
        }

        // Asumiendo que el botón de retroceso contiene el texto "<"
        composeRule.onNodeWithText("<").performClick()

        // Verificamos que se llamó al popBackStack
        verify(exactly = 1) { fakeNavController.popBackStack() }
    }

    @Test
    fun `clic_en_boton_perfil_navega_a_perfil_Invitado`() { // Renombrado
        val fakeNavController = mockk<NavController>(relaxed = true)

        // Usamos un repositorio vacío para mantener la consistencia
        val viewModel = PostViewModel(repository = FakePostRepository(emptyList()))

        composeRule.setContent {
            PostScreen(viewModel = viewModel, navController = fakeNavController)
        }

        // Asumiendo que el botón de perfil contiene el texto ">"
        composeRule.onNodeWithText(">").performClick()

        // Verificamos la navegación. Usamos 'any()' si la navegación usa NavOptions.
        verify(exactly = 1) {
            fakeNavController.navigate(
                route = eq("perfil/Invitado"),
                navOptions = any(),
                navigatorExtras = any()
            )
        }
    }
}