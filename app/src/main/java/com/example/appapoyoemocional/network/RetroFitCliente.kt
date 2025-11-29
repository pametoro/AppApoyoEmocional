package com.example.appapoyoemocional.network

import com.example.appapoyoemocional.data.TokenManager // Importa el TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitCliente {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    // Interceptor para agregar el token de autenticación a todas las peticiones
    private val authInterceptor = object: Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val newRequest = TokenManager.authToken?.let { token ->
                // Si hay un token, se agrega el encabezado de Autorización (Bearer Token)
                originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            } ?: originalRequest // Si no hay token, se usa la petición original
            return chain.proceed(newRequest)
        }
    }

    // Cliente OkHttpClient que incluye el Interceptor de autenticación
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    // Instancia de Retrofit configurada para usar el cliente con interceptor
    private val retrofit: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Servicios disponibles para ser llamados en ViewModels/Repositories
    val authService: AuthService by lazy{
        retrofit.create(AuthService::class.java)
    }

    val usuarioService: UsuarioService by lazy {
        retrofit.create(UsuarioService::class.java)
    }
}