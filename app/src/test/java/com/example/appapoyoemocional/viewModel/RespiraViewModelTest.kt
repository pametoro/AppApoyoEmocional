package com.example.appapoyoemocional.viewModel

import com.example.appapoyoemocional.data.modelo.VideoRespiracion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RespiraViewModelTest {

    @Test
    fun `al inicializar el ViewModel debe contener el video de respiracion`() {
        val viewModel = RespiraViewModel()

        val estado = viewModel.estado.value

        assertEquals("Ejercicio de respiración", estado.titulo)
        assertEquals("https://www.youtube.com/watch?v=adpR2UQTElk", estado.url)
    }
}
