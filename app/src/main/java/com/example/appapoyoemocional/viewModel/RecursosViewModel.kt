package com.example.appapoyoemocional.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appapoyoemocional.data.Resource // DTO que coincide con ResourceDto.java del backend
// Asegúrate de que RecursosUIState exista, pero usaremos el DTO de la API (Resource)
// import com.example.appapoyoemocional.data.modelo.RecursosUIState
import com.example.appapoyoemocional.network.RetroFitCliente
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecursosViewModel : ViewModel() {

    // --- ESTADOS PARA LA UI ---

    // Estado principal: La lista de recursos de la API.
    // Usamos el DTO 'Resource' que se espera del backend (ResourceDto).
    private val _recursos = MutableStateFlow<List<Resource>>(emptyList())
    val recursos: StateFlow<List<Resource>> = _recursos // Expone la lista para la UI

    // Estado para indicar si se está cargando (útil para mostrar un Spinner/Progreso)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Estado para manejar errores de conexión o servidor
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        // Cargar los recursos automáticamente al inicializar el ViewModel
        cargarRecursos()
    }

    /**
     * Reemplaza la lógica estática para cargar los recursos desde el endpoint /api/resources.
     */
    fun cargarRecursos() {
        _isLoading.value = true // Inicia la carga
        _error.value = null // Limpia cualquier error previo

        viewModelScope.launch {
            try {
                // 1. Llama al servicio de recursos de Retrofit (GET /api/resources)
                val response = RetroFitCliente.resourceService.getAllResources()

                // 2. Verifica si la respuesta HTTP es exitosa (código 200)
                if (response.isSuccessful && response.body() != null) {
                    // 3. Actualiza el StateFlow con los datos obtenidos
                    _recursos.value = response.body()!!

                } else {
                    // Manejo de errores del servidor (ej. 404, 500)
                    val errorCode = response.code()
                    val errorBody = response.errorBody()?.string() ?: "Error desconocido"
                    _error.value = "Error al obtener recursos: Código $errorCode. Detalle: $errorBody"
                    println(_error.value)
                }
            } catch (e: Exception) {
                // Manejo de errores de red (ej. servidor apagado, sin internet)
                _error.value = "Error de conexión: No se pudo contactar al servidor. ${e.message}"
                println("Error de conexión con /api/resources: ${e.message}")
            } finally {
                _isLoading.value = false // Finaliza la carga, sin importar el resultado
            }
        }
    }
}