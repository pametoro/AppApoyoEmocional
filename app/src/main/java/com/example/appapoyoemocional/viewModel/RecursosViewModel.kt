package com.example.appapoyoemocional.viewModel

import androidx.lifecycle.ViewModel
import com.example.appapoyoemocional.data.modelo.RecursosUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecursosViewModel  : ViewModel() {
    private val _consejos = MutableStateFlow<List<RecursosUIState>>(emptyList())
    val consejos: StateFlow<List<RecursosUIState>> = _consejos

    init {
        cargarConsejos()
    }

    private fun cargarConsejos() {
        _consejos.value = listOf(
            RecursosUIState(1, "Respira profundo y exhala lentamente."),
            RecursosUIState(2, "Escribe lo que sientes sin juzgarte."),
            RecursosUIState(3, "Escucha tu canción favorita."),
            RecursosUIState(4, "Sal a caminar unos minutos.")
        )
    }
}