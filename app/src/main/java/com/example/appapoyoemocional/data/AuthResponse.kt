package com.example.appapoyoemocional.data
// modelo representa la respuesta exitosa del microservicio autenticarUsuario

data class AuthResponse(
    val token: String,
    val userId: Int,
    val nombreUsuario: String
)
