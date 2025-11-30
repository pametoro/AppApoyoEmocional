package com.example.appapoyoemocional.data

import com.google.gson.annotations.SerializedName

// Estructura para GET /api/resources
data class Resource(
    @SerializedName("id")
    val id: Long,
    @SerializedName("content")
    val content: String
)
