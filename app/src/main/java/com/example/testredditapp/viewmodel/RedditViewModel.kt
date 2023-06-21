package com.example.testredditapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testredditapp.data.RedditPost
import com.example.testredditapp.model.RedditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RedditViewModel @Inject  constructor(
    private val redditRepository: RedditRepository
) : ViewModel() {

    private val _posts = MutableLiveData<PagingData<RedditPost>>()
    val posts: LiveData<PagingData<RedditPost>> = _posts

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        redditRepository.fetchPost().cachedIn(viewModelScope)
            .observeForever { pagingData ->
            _posts.value = pagingData
        }
    }
}