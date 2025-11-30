package com.example.appapoyoemocional.data

import com.google.gson.annotations.SerializedName

// Estructura para la respuesta de /api/users o /api/users/{id}
data class UserResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String
)
