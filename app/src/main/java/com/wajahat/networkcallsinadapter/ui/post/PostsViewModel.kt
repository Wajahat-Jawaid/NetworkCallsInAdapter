package com.wajahat.networkcallsinadapter.ui.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.wajahat.networkcallsinadapter.data.model.Post
import com.wajahat.networkcallsinadapter.data.model.response.PostsResponse
import com.wajahat.networkcallsinadapter.data.repository.PostsRepository
import com.wajahat.networkcallsinadapter.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostsViewModel(private val repository: PostsRepository) : ViewModel() {

    private var _postsResponse = MutableLiveData<Resource<PostsResponse>>()
    val postsResponse: MutableLiveData<Resource<PostsResponse>>
        get() = _postsResponse

    fun getPosts() {
        viewModelScope.launch {
            _postsResponse.postValue(Resource.loading(data = null))
            try {
                _postsResponse.postValue(Resource.success(data = repository.getPosts(POSTS_COUNT)))
            } catch (exception: Exception) {
                _postsResponse.postValue(Resource.error(data = null, message = exception.message ?: "Error!!!"))
            }
        }
    }

    fun getSubscribersCount(blogUrl: String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = repository.getSubscribersCount(blogUrl)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error!!!"))
        }
    }

    /** Calculate the difference between two post's date */
    fun isNewDay(previousPost: Post, currentPost: Post): Boolean {
        return previousPost.getDateObj().time - DAY_MILLISECONDS > currentPost.getDateObj().time
    }

    companion object {
        // Milliseconds in a single day
        private const val DAY_MILLISECONDS = 60000 * 60 * 24L
        private const val POSTS_COUNT = 10
    }
}