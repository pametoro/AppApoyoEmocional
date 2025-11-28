package com.example.appapoyoemocional.repositoryTest

import android.net.Uri
import com.example.appapoyoemocional.repository.PerfilRepositorio
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
        val nuevaUri = Uri.parse("content://imagen_nueva")

        repositorio.updateImage(nuevaUri)

        val perfilActualizado = repositorio.getProfile()
        assertEquals(nuevaUri, perfilActualizado.imagenUri)
    }

    @Test
    fun `updateImage debe mantener inmutabilidad del objeto PerfilDeUsuario`() = runTest {
        val repositorio = PerfilRepositorio()
        val perfilInicial = repositorio.getProfile()
        val nuevaUri = Uri.parse("content://otra_imagen")

        repositorio.updateImage(nuevaUri)

        val perfilActualizado = repositorio.getProfile()

        // El objeto inicial no debe ser el mismo que el actualizado
        assertNotEquals(perfilInicial, perfilActualizado)
        assertEquals(nuevaUri, perfilActualizado.imagenUri)
    }
}

