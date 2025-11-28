package com.example.appapoyoemocional.data.modelo

data class EmocionUIState (
    val titulo: String = "Tu espacio Emocional",
    val descripcion: String = "En este espacio puedes escribir tus pensamientos, sentimientos o todo aquello que quieras expresar.",
    val nombreUsuario: String = "",
    val emocionTexto: String = "",
    val emocionGuardada: Boolean = false
)