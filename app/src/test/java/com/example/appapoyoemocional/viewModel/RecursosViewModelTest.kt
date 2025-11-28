package com.example.appapoyoemocional.viewModel

import com.example.appapoyoemocional.data.modelo.RecursosUIState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RecursosViewModelTest {

    @Test
    fun `al inicializar el ViewModel debe cargar los consejos`() {
        val viewModel = RecursosViewModel()

        val consejos = viewModel.consejos.value

        assertEquals(4, consejos.size)
        assertEquals(RecursosUIState(1, "Respira profundo y exhala lentamente."), consejos[0])
        assertEquals(RecursosUIState(2, "Escribe lo que sientes sin juzgarte."), consejos[1])
        assertEquals(RecursosUIState(3, "Escucha tu canción favorita."), consejos[2])
        assertEquals(RecursosUIState(4, "Sal a caminar unos minutos."), consejos[3])
    }
}
