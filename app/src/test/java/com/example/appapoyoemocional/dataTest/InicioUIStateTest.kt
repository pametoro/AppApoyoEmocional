package com.example.appapoyoemocional.dataTest


import com.example.appapoyoemocional.data.modelo.InicioUIState
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.booleans.shouldBeTrue

class InicioUIStateTest : BehaviorSpec({

    // Definición de valores por defecto para referencia en los tests
    val DEFAULT_TITULO = "Bienvenido a la Aplicacion de Apoyo Emocional"
    val DEFAULT_DESCRIPCION = "Esta aplicación te ayuda a gestionar tus emociones, reflexionar sobre tu bienestar y acceder a recursos que promuevan tu salud mental."

    // --- ESPECIFICACIÓN DE COMPORTAMIENTO (Kotest BehaviorSpec) ---

    Given("Una instancia de InicioUIState sin argumentos (estado por defecto)") {
        val estadoInicial = InicioUIState()

        When("Se verifica el estado por defecto") {
            Then("1. Los campos 'titulo' y 'descripcion' deben tener los valores esperados") {
                estadoInicial.titulo shouldBe DEFAULT_TITULO
                estadoInicial.descripcion shouldBe DEFAULT_DESCRIPCION
            }

            Then("2. El campo 'nombre' debe ser null") {
                estadoInicial.nombre.shouldBeNull()
            }

            Then("3. El campo 'mostrarBoton' debe ser true") {
                estadoInicial.mostrarBoton.shouldBeTrue()
            }
        }
    }

    Given("Dos instancias de InicioUIState con los mismos valores") {
        val estado1 = InicioUIState(nombre = "Invitado", mostrarBoton = false)
        val estado2 = InicioUIState(nombre = "Invitado", mostrarBoton = false)

        When("Se comparan los objetos") {
            Then("1. Deben ser considerados iguales (gracias a equals de data class)") {
                estado1 shouldBe estado2
            }

            Then("2. Deben tener el mismo código hash") {
                estado1.hashCode() shouldBe estado2.hashCode()
            }
        }
    }

    Given("Una instancia con valores personalizados") {
        val estadoOriginal = InicioUIState(
            nombre = "Carlos",
            mostrarBoton = true
        )

        When("Se usa la función copy() para crear una versión modificada") {
            val estadoModificado = estadoOriginal.copy(nombre = "Ana", mostrarBoton = false)

            Then("1. El título y la descripción por defecto deben permanecer iguales") {
                estadoModificado.titulo shouldBe estadoOriginal.titulo
                estadoModificado.descripcion shouldBe estadoOriginal.descripcion
            }

            Then("2. Los campos modificados deben reflejar los nuevos valores") {
                estadoModificado.nombre shouldBe "Ana"
                estadoModificado.mostrarBoton shouldBe false
            }

            Then("3. El objeto modificado no debe ser igual al original") {
                estadoModificado shouldNotBe estadoOriginal
            }
        }
    }
})