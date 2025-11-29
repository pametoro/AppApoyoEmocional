package com.example.appapoyoemocional.network

import com.example.appapoyoemocional.data.modelo.PerfilDeUsuario
import com.example.appapoyoemocional.data.modelo.UsuarioUIState
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.GET

interface UsuarioService {
    @POST("/api/usuarios/registrar")
    suspend fun registrarUsuario(@Body usuario: UsuarioUIState): Response<Unit>

    @GET("/api/usuarios/{id}")
    suspend fun obtenerPerfilDeUsuario(@Path("id") userId: Int): Response<PerfilDeUsuario>
}