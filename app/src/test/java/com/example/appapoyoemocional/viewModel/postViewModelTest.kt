package com.example.appapoyoemocional.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appapoyoemocional.data.modelo.Post
import com.example.appapoyoemocional.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository = PostRepository()
) : ViewModel() {

    private val _postList = MutableStateFlow<List<Post>>(emptyList())
    val postList: StateFlow<List<Post>> = _postList

    init {
        // Puedes dejarlo o quitarlo, según prefieras
        fetchPosts()
    }

    // Ahora es público para poder llamarlo en los tests
    fun fetchPosts() {
        viewModelScope.launch {
            try {
                _postList.value = repository.getPosts()
            } catch (e: Exception) {
                println("Error al obtener datos: ${e.localizedMessage}")
            }
        }
    }
}
