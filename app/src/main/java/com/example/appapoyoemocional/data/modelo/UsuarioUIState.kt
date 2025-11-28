package com.example.appapoyoemocional.data.modelo

data class UsuarioUIState (
    val nombre : String = "",
    val correo : String = "",
    val clave : String = "",
    val direccion : String = "",
    val aceptaTerminos : Boolean = false,
    val errores : UsuarioErrores = UsuarioErrores()
)