package com.example.appapoyoemocional.data.modelo

import android.net.Uri

data class PerfilDeUsuario (
    val id: Int,
    val nombre: String,
    val imagenUri: Uri? = null
)