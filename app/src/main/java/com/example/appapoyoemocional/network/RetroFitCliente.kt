package com.example.appapoyoemocional.network

import com.example.appapoyoemocional.data.TokenManager
// --- CORRECCIÓN: Añadir las importaciones que faltan ---
import com.example.appapoyoemocional.network.AuthService
import com.example.appapoyoemocional.network.ResourceService
import com.example.appapoyoemocional.network.UsuarioService
// ----------------------------------------------------
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitCliente {
    private const val BASE_URL = "http://10.0.2.2:8080"

    private val authInterceptor = object: Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            val newRequest = TokenManager.authToken?.let { token ->
                originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            } ?: originalRequest

            return chain.proceed(newRequest)
        }
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy{
        retrofit.create(AuthService::class.java)
    }

    val usuarioService: UsuarioService by lazy {
        retrofit.create(UsuarioService::class.java)
    }

    val resourceService: ResourceService by lazy {
        retrofit.create(ResourceService::class.java)
    }
}
