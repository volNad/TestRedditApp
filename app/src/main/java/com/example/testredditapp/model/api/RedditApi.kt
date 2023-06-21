package com.example.testredditapp.model.api

import com.example.testredditapp.data.RedditResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {
    @GET("top.json")
    suspend fun fetchPosts(
        @Query("limit") loadSize: Int = 0,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("count") seen: Int = 0
    ): Response<RedditResponse>
}