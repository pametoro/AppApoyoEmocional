package com.example.appapoyoemocional.data
//modelo representa los datos que el microservicio
// autenticarUsuario espera para iniciar sesión

data class LoginRequest(
    val correo: String,
    val clave: String
)
