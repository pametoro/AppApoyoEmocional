package com.example.appapoyoemocional.repository

import android.net.Uri
import com.example.appapoyoemocional.data.modelo.PerfilDeUsuario

open class PerfilRepositorio {

    private var perfilActual = PerfilDeUsuario(
        id = 1,
        nombre = "Usuario",
        imagenUri = null
    )

    open fun getProfile(): PerfilDeUsuario = perfilActual

    suspend fun updateImage(uri: Uri?){
        perfilActual = perfilActual.copy(imagenUri = uri)
    }
}