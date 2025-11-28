package com.example.appapoyoemocional.viewModel

import com.example.appapoyoemocional.data.modelo.EmocionUIState
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class EmocionViewModelTest : StringSpec({

    "actualizarNombre debe cambiar el nombreUsuario en el estado" {
        val viewModel = EmocionViewModel()

        viewModel.actualizarNombre("Pamela")

        viewModel.estado.value.nombreUsuario shouldBe "Pamela"
    }

    "actualizarEmocion debe cambiar el emocionTexto en el estado" {
        val viewModel = EmocionViewModel()

        viewModel.actualizarEmocion("Feliz")

        viewModel.estado.value.emocionTexto shouldBe "Feliz"
    }

    "guardarEmocion debe devolver false si emocionTexto está vacío" {
        val viewModel = EmocionViewModel()

        viewModel.actualizarEmocion("")

        viewModel.guardarEmocion() shouldBe false
    }

    "guardarEmocion debe devolver true si emocionTexto no está vacío" {
        val viewModel = EmocionViewModel()

        viewModel.actualizarEmocion("Motivada")

        viewModel.guardarEmocion() shouldBe true
    }
})
