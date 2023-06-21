package com.example.testredditapp.model

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.testredditapp.data.RedditPost
import com.example.testredditapp.model.api.RedditApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RedditRepository @Inject constructor(
    private val redditApi: RedditApi
) {

    fun fetchPost(): LiveData<PagingData<RedditPost>> {
        return Pager(
            PagingConfig(pageSize = 40, enablePlaceholders = false, prefetchDistance = 3)
        ) {
            RedditPagingSource(redditApi)
        }.liveData
    }
}