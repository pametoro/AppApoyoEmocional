
package com.example.appapoyoemocional.repository

import com.example.appapoyoemocional.data.remote.RetrofitInstance
import com.example.appapoyoemocional.data.modelo.Post

open class PostRepository {
    // Llama al getter 'api' del singleton
    private val apiService = RetrofitInstance.api

    open suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }
}