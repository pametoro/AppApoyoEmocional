package com.example.appapoyoemocional.repositoryTest

import com.example.appapoyoemocional.data.modelo.Post
import com.example.appapoyoemocional.data.remote.ApiService
import com.example.appapoyoemocional.data.remote.RetrofitInstance
import com.example.appapoyoemocional.repository.PostRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach // Para limpiar el mock
import org.junit.jupiter.api.assertThrows

@OptIn(ExperimentalCoroutinesApi::class)
class PostRepositoryTest {

    // Método para asegurar que el mock se elimine después de cada prueba
    @AfterEach
    fun tearDown() {
        RetrofitInstance.setMockApi(null)
    }

    @Test
    fun `getPosts debe devolver lista simulada`() = runTest {
        // Mock de ApiService
        val mockApi = mockk<ApiService>()
        val fakePosts = listOf(
            Post(userId = 1, id = 1, title = "Titulo 1", body = "Contenido 1"),
            Post(userId = 2, id = 2, title = "Titulo 2", body = "Contenido 2")
        )

        // 1. Configurar el comportamiento del mock
        coEvery { mockApi.getPosts() } returns fakePosts

        // 2. Inyectar el mock usando el método auxiliar
        RetrofitInstance.setMockApi(mockApi)

        val repo = PostRepository()
        val result = repo.getPosts()

        // 3. Verificación
        assertEquals(fakePosts, result)
    }

    @Test
    fun `getPosts debe lanzar excepcion si api falla`() = runTest {
        val mockApi = mockk<ApiService>()

        // 1. Configurar el mock para lanzar una excepción
        coEvery { mockApi.getPosts() } throws RuntimeException("Error simulado")

        // 2. Inyectar el mock
        RetrofitInstance.setMockApi(mockApi)

        val repo = PostRepository()

        // 3. Verificación de excepción usando assertThrows de JUnit 5
        val exception = assertThrows<RuntimeException> { repo.getPosts() }

        assertEquals("Error simulado", exception.message)
    }
}