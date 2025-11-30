package com.example.appapoyoemocional.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val correo: String,
    @SerializedName("password")
    val clave: String
)