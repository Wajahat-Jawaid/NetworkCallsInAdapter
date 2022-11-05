package com.wajahat.networkcallsinadapter.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wajahat.networkcallsinadapter.ui.post.PostsViewModel
import com.wajahat.networkcallsinadapter.data.repository.PostsRepository

/**
 * Factory class to store the ViewModel references so as they are not recreating every time.
 * */
class ViewModelFactory(private val repository: PostsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}