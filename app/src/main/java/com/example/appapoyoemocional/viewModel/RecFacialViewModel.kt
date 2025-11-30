package com.example.appapoyoemocional.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecFacialViewModel : ViewModel() {
    // Estado para la emoción detectada
    private val _emocion = MutableStateFlow("Esperando rostro...")
    val emocion: StateFlow<String> = _emocion

    // Simulación de estados para que la pantalla compile
    private val _faces = MutableStateFlow(emptyList<Any>())
    val faces: StateFlow<List<Any>> = _faces

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    /**
     * Función que el CameraFaceDetector usará para actualizar la emoción.
     */
    fun updateDetectedEmotion(emotion: String) {
        _emocion.value = emotion
    }

    // El método processImage ya no es necesario si usamos la cámara en vivo, pero se mantiene si lo necesitas para pruebas
    fun processImage(image: Any) {
        // Lógica de procesamiento de imagen estática (ahora ignorada)
    }
}