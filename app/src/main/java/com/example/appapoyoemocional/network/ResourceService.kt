package com.example.appapoyoemocional.network

import com.example.appapoyoemocional.data.Resource
import retrofit2.Response
import retrofit2.http.GET

interface ResourceService {

    // Conecta con el ResourceController del backend: GET /api/resources
    @GET("/api/resources")
    suspend fun getAllResources(): Response<List<Resource>>
}