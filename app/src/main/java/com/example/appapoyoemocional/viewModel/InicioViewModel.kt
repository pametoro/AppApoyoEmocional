package com.example.appapoyoemocional.viewModel

import androidx.lifecycle.ViewModel
import com.example.appapoyoemocional.data.modelo.InicioUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InicioViewModel : ViewModel(){
    private val _estado = MutableStateFlow(InicioUIState())
    val estado: StateFlow<InicioUIState> = _estado

    fun ocultarBoton() {
        _estado.value = _estado.value.copy(mostrarBoton = false)
    }

    fun actualizarDescripcion(texto: String) {
        _estado.value = _estado.value.copy(descripcion = texto)
    }
}