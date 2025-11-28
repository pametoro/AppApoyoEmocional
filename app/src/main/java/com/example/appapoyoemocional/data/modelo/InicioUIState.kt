package com.example.appapoyoemocional.data.modelo

data class InicioUIState (
    val titulo: String = "Bienvenido a la Aplicacion de Apoyo Emocional",
    val nombre: String? = null,
    val descripcion: String = "Esta aplicación te ayuda a gestionar tus emociones, reflexionar sobre tu bienestar y acceder a recursos que promuevan tu salud mental.",
    val mostrarBoton: Boolean = true
)