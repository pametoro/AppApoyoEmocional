package com.example.appapoyoemocional.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appapoyoemocional.data.LoginRequest
import com.example.appapoyoemocional.data.TokenManager
import com.example.appapoyoemocional.network.RetroFitCliente
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    // --- MODIFICACIÓN 1: Creación del StateFlow ---
    // Este estado será observado por la pantalla de Login.
    // Inicia en 'false' (no se ha logueado exitosamente).
    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso: StateFlow<Boolean> = _loginExitoso

    // Función para manejar el inicio de sesión
    fun iniciarSesion(correo: String, clave: String) {
        // Inicia una coroutine en el ámbito del ViewModel
        viewModelScope.launch {
            try {
                // 1. Crear el objeto de solicitud
                val request = LoginRequest(correo, clave)

                // 2. Llamar al servicio de autenticación
                val response = RetroFitCliente.authService.login(request)

                // 3. Manejar la respuesta
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    authResponse?.token?.let { token ->
                        // 4. Guardar el token de forma global
                        TokenManager.authToken = token
                        println("Login Exitoso. Token guardado.")

                        // --- MODIFICACIÓN 2: Actualizar el estado ---
                        // Notifica a la UI que el login fue exitoso.
                        _loginExitoso.value = true
                    }
                } else {
                    // Manejar errores como 401 Unauthorized
                    println("Login Fallido: Código ${response.code()}")
                    _loginExitoso.value = false // Opcional: notificar el fallo
                }
            } catch (e: Exception) {
                // Manejar errores de conexión
                println("Error de conexión/red: ${e.message}")
                _loginExitoso.value = false // Opcional: notificar el fallo
            }
        }
    }

    // --- MODIFICACIÓN 3: Función para reiniciar el estado ---
    // Para evitar que se navegue de nuevo si el usuario vuelve a la pantalla de login.
    fun resetLoginState() {
        _loginExitoso.value = false
    }
}