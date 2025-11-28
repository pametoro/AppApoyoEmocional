package com.example.appapoyoemocional.viewModel

import android.net.Uri
import com.example.appapoyoemocional.data.modelo.PerfilDeUsuario
import com.example.appapoyoemocional.repository.PerfilRepositorio
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PerfilViewModelTest {

    @Test
    fun `setImage debe actualizar el StateFlow y llamar al repositorio`() = runTest {
        // Arrange: mock del repositorio
        val mockRepositorio = mockk<PerfilRepositorio>(relaxed = true)
        val perfilInicial = PerfilDeUsuario(id = 1, nombre = "Usuario", imagenUri = null)

        every { mockRepositorio.getProfile() } returns perfilInicial

        val viewModel = PerfilViewModel(mockRepositorio)

        // Act: en vez de Uri.parse(), usamos un mock de Uri
        val nuevaUri = mockk<Uri>(relaxed = true)
        viewModel.setImage(nuevaUri)

        // Assert: el estado se actualiza en el ViewModel
        assertEquals(nuevaUri, viewModel.imagenUri.value)

        // Assert: se llama al repositorio con la nueva Uri
        coVerify { mockRepositorio.updateImage(nuevaUri) }
    }
}

