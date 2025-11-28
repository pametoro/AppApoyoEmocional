package com.example.appapoyoemocional.viewModel

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class InicioViewModelTest : StringSpec({

    "ocultarBoton debe poner mostrarBoton en false" {
        val viewModel = InicioViewModel()

        viewModel.ocultarBoton()

        viewModel.estado.value.mostrarBoton shouldBe false
    }

    "actualizarDescripcion debe cambiar el texto de descripcion" {
        val viewModel = InicioViewModel()

        viewModel.actualizarDescripcion("Bienvenido a la app")

        viewModel.estado.value.descripcion shouldBe "Bienvenido a la app"
    }
})
