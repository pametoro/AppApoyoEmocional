package com.example.appapoyoemocional.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.appapoyoemocional.data.modelo.VideoRespiracion

class RespiraViewModel : ViewModel() {
    private val _estado = mutableStateOf(
        VideoRespiracion(
            titulo = "Ejercicio de respiración",

            url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        )
    )
    val estado: State<VideoRespiracion> = _estado
}