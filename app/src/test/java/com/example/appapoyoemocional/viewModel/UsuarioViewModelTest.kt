package com.example.appapoyoemocional.viewModel

import com.example.appapoyoemocional.data.modelo.UsuarioErrores
import com.example.appapoyoemocional.data.modelo.UsuarioUIState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UsuarioViewModelTest {

    @Test
    fun `onNombreChange debe actualizar el nombre y limpiar error`() {
        val viewModel = UsuarioViewModel()

        viewModel.onNombreChange("Pamela")

        assertEquals("Pamela", viewModel.estado.value.nombre)
        assertNull(viewModel.estado.value.errores.nombre)
    }

    @Test
    fun `onCorreoChange debe actualizar el correo y limpiar error`() {
        val viewModel = UsuarioViewModel()

        viewModel.onCorreoChange("correo@valido.com")

        assertEquals("correo@valido.com", viewModel.estado.value.correo)
        assertNull(viewModel.estado.value.errores.correo)
    }

    @Test
    fun `validarFormulario debe devolver false si hay campos inválidos`() {
        val viewModel = UsuarioViewModel()

        viewModel.onNombreChange("")
        viewModel.onCorreoChange("correoInvalido")
        viewModel.onClaveChange("123")
        viewModel.onDireccionChange("")

        val resultado = viewModel.validarFormulario()

        assertFalse(resultado)
        assertEquals("NO PUEDE ESTAR VACÍO", viewModel.estado.value.errores.nombre)
        assertEquals("CORREO INVÁLIDO", viewModel.estado.value.errores.correo)
        assertEquals("DEBE TENER AL MENOS 8 CARACTERES", viewModel.estado.value.errores.clave)
        assertEquals("NO PUEDE ESTAR VACÍO", viewModel.estado.value.errores.direccion)
    }

    @Test
    fun `validarFormulario debe devolver true si todos los campos son válidos`() {
        val viewModel = UsuarioViewModel()

        viewModel.onNombreChange("Pamela")
        viewModel.onCorreoChange("pamela@correo.com")
        viewModel.onClaveChange("12345678")
        viewModel.onDireccionChange("Santiago")
        viewModel.onAceptarTerminosChange(true)

        val resultado = viewModel.validarFormulario()

        assertTrue(resultado)
        assertNull(viewModel.estado.value.errores.nombre)
        assertNull(viewModel.estado.value.errores.correo)
        assertNull(viewModel.estado.value.errores.clave)
        assertNull(viewModel.estado.value.errores.direccion)
    }
}
