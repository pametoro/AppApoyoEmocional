
package com.example.appapoyoemocional.repository

import com.example.appapoyoemocional.data.remote.RetrofitInstance
import com.example.appapoyoemocional.data.modelo.Post

class PostRepository {
    // Llama al getter 'api' del singleton
    private val apiService = RetrofitInstance.api

    suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }
}