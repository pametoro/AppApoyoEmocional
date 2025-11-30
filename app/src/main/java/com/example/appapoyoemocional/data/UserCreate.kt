package com.example.appapoyoemocional.data

import com.google.gson.annotations.SerializedName

// Estructura para POST /api/users
data class UserCreate(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
