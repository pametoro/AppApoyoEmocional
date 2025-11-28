package com.example.appapoyoemocional.viewModel

import android.net.Uri
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
        val uriInicial = Uri.parse("content://imagen_inicial")
        every { mockRepositorio.getProfile().imagenUri } returns uriInicial

        val viewModel = PerfilViewModel(mockRepositorio)

        // Act: actualizar imagen
        val nuevaUri = Uri.parse("content://imagen_nueva")
        viewModel.setImage(nuevaUri)

        // Assert: el estado se actualiza
        assertEquals(nuevaUri, viewModel.imagenUri.value)

        // Assert: se llama al repositorio
        coVerify { mockRepositorio.updateImage(nuevaUri) }
    }
}
