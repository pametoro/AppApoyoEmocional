package com.example.appapoyoemocional.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appapoyoemocional.repository.PerfilRepositorio
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilViewModel (
    private val repositorio: PerfilRepositorio = PerfilRepositorio(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri: StateFlow<Uri?> = _imagenUri

    init {

        viewModelScope.launch(dispatcher) {

            _imagenUri.value = repositorio.getProfile().imagenUri
        }
    }

    fun setImage(uri: Uri?) {
        // Usamos el dispatcher inyectado
        viewModelScope.launch(dispatcher) {
            _imagenUri.value = uri
            repositorio.updateImage(uri)
        }
    }
}