package com.example.appapoyoemocional.network

import com.example.appapoyoemocional.data.AuthResponse
import com.example.appapoyoemocional.data.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
interface AuthService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
}