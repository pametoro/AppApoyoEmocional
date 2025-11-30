package com.example.appapoyoemocional.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // Importación necesaria para corrutinas
import com.example.appapoyoemocional.data.UserCreate // DTO para enviar datos de registro al backend
import com.example.appapoyoemocional.data.UserResponse // DTO para recibir la respuesta del usuario registrado
import com.example.appapoyoemocional.data.modelo.UsuarioErrores
import com.example.appapoyoemocional.data.modelo.UsuarioUIState
import com.example.appapoyoemocional.network.RetroFitCliente // Importación necesaria para el servicio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch // Importación necesaria para corrutinas

class UsuarioViewModel : ViewModel() {
    private val _estado = MutableStateFlow(UsuarioUIState())

    val estado: StateFlow<UsuarioUIState> = _estado

    // --- Funciones de manejo de UI (Se mantienen) ---

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    // Nota: El campo 'direccion' no está en el DTO de registro UserCreate.kt.
    // Si la dirección no se usa en el backend, se puede ignorar en la petición de registro.
    fun onDireccionChange(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "NO PUEDE ESTAR VACÍO" else null,
            correo = if (!estadoActual.correo.contains("@")) "CORREO INVÁLIDO" else null,
            clave = if (estadoActual.clave.length < 8) "DEBE TENER AL MENOS 8 CARACTERES" else null,
            // Manteniendo la validación local de 'direccion'
            direccion = if (estadoActual.direccion.isBlank()) "NO PUEDE ESTAR VACÍO" else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    // --- NUEVA FUNCIÓN PARA REGISTRO CON CONEXIÓN AL BACKEND ---
    /**
     * Intenta registrar un nuevo usuario llamando al endpoint /api/users del backend.
     * Utiliza los campos almacenados en el UIState.
     */
    fun registrarUsuario() {
        // Asegúrate de que el formulario es válido antes de intentar el registro
        if (!validarFormulario()) {
            println("El formulario tiene errores locales. No se procede con el registro.")
            return
        }

        val estadoActual = _estado.value

        // El DTO de registro solo necesita username, email y password
        val userCreate = UserCreate(
            username = estadoActual.nombre, // Mapeamos 'nombre' a 'username'
            email = estadoActual.correo,
            password = estadoActual.clave
        )

        // Usamos viewModelScope.launch para ejecutar la llamada de red de forma asíncrona
        viewModelScope.launch {
            try {
                // Llama al servicio corregido: POST /api/users
                val response = RetroFitCliente.usuarioService.registrarUsuario(userCreate)

                if (response.isSuccessful) {
                    val nuevoUsuario = response.body()
                    println("Registro Exitoso. ID de usuario: ${nuevoUsuario?.id}, Username: ${nuevoUsuario?.username}")

                    // Aquí deberías setear un estado de éxito para que FormularioScreen sepa que debe navegar
                    // Ejemplo: _estado.update { it.copy(registroExitoso = true) }

                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Registro Fallido (Código ${response.code()}): $errorBody")

                    // Aquí puedes setear un estado de error específico para mostrar en la UI
                    // Ejemplo: _estado.update { it.copy(registroError = "El correo o usuario ya existe.") }
                }
            } catch (e: Exception) {
                println("Error de conexión durante el registro: ${e.message}")
                // Manejar errores de red
            }
        }
    }

    // --- (Tu lógica de obtención de perfil iría aquí) ---
    // fun obtenerPerfil(userId: Int) { ... }
}
