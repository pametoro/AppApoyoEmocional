package com.example.appapoyoemocional.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Usaremos un campo de respaldo para poder inyectar el mock en el test.
    private var _api: ApiService? = null

    // El getter público verifica si hay un mock inyectado, sino usa la instancia real.
    val api: ApiService
        get() {
            return _api ?: retrofit.create(ApiService::class.java)
        }

    // Método auxiliar para inyección en tests
    fun setMockApi(mockApi: ApiService?) {
        _api = mockApi
    }
}



