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

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso: StateFlow<Boolean> = _loginExitoso

    // Función para manejar el inicio de sesión
    fun iniciarSesion(correo: String, clave: String) {
        viewModelScope.launch {
            try {
                // Crear el objeto de solicitud
                val request = LoginRequest(correo, clave)

                // Llamar al servicio de autenticación
                val response = RetroFitCliente.authService.login(request)

                // Manejar la respuesta
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    authResponse?.token?.let { token ->
                        TokenManager.authToken = token
                        println("Login Exitoso. Token guardado.")
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

    fun resetLoginState() {
        _loginExitoso.value = false
    }
}