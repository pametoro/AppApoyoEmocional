package com.example.appapoyoemocional.viewModel

import androidx.lifecycle.ViewModel
import com.example.appapoyoemocional.data.modelo.EmocionUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EmocionViewModel : ViewModel() {
    private val _estado = MutableStateFlow(EmocionUIState())
    val estado: StateFlow<EmocionUIState> = _estado

    // ARREGLO CLAVE: La función debe esperar un parámetro de tipo String
    fun actualizarNombre(nombre: String) {
        _estado.value = _estado.value.copy(nombreUsuario = nombre)
    }

    fun actualizarEmocion(texto: String) {
        _estado.value = _estado.value.copy(emocionTexto = texto)
    }

    fun guardarEmocion(): Boolean {
        val emocionValida = _estado.value.emocionTexto.isNotBlank()
        if (emocionValida) {
            // Lógica para guardar la emoción (si se implementara la persistencia)
        }
        return emocionValida
    }
}