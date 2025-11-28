package com.example.appapoyoemocional.repositoryTest

import android.net.Uri
import com.example.appapoyoemocional.repository.PerfilRepositorio
import io.mockk.mockk // Importar mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PerfilRepositorioTest {

    @Test
    fun `getProfile debe devolver el perfil inicial`() {
        val repositorio = PerfilRepositorio()

        val perfil = repositorio.getProfile()

        assertEquals(1, perfil.id)
        assertEquals("Usuario", perfil.nombre)
        assertNull(perfil.imagenUri)
    }

    @Test
    fun `updateImage debe actualizar la imagen del perfil`() = runTest {
        val repositorio = PerfilRepositorio()

        // CORRECCIÓN LÍNEA 27: Usar mockk en lugar de Uri.parse()
        val nuevaUri = mockk<Uri>(relaxed = true)

        repositorio.updateImage(nuevaUri)

        val perfilActualizado = repositorio.getProfile()
        assertEquals(nuevaUri, perfilActualizado.imagenUri)
    }

    @Test
    fun `updateImage debe mantener inmutabilidad del objeto PerfilDeUsuario`() = runTest {
        val repositorio = PerfilRepositorio()
        val perfilInicial = repositorio.getProfile()

        // CORRECCIÓN LÍNEA 39: Usar mockk en lugar de Uri.parse()
        val nuevaUri = mockk<Uri>(relaxed = true)

        repositorio.updateImage(nuevaUri)

        val perfilActualizado = repositorio.getProfile()

        // Usamos assertNotSame para verificar que la referencia del objeto ha cambiado
        assertNotSame(perfilInicial, perfilActualizado)
        assertEquals(nuevaUri, perfilActualizado.imagenUri)
    }
}