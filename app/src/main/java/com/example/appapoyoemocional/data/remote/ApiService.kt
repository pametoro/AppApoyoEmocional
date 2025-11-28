package com.example.appapoyoemocional.data.remote

import com.example.appapoyoemocional.data.modelo.Post
import retrofit2.http.GET

interface ApiService {

    @GET("/posts")
    suspend fun getPosts(): List<Post>
}