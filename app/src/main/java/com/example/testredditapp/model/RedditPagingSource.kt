package com.example.testredditapp.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testredditapp.data.RedditPost
import com.example.testredditapp.model.api.RedditApi
import retrofit2.HttpException
import java.io.IOException

class RedditPagingSource(
    private val redditApi: RedditApi
) : PagingSource<String, RedditPost>() {


    override fun getRefreshKey(state: PagingState<String, RedditPost>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RedditPost> {
        return try {
            val response = redditApi.fetchPosts(loadSize = params.loadSize, after = params.key,
                seen = params.loadSize)
            val listing = response.body()?.data
            val redditPosts = listing?.children?.map { it.data }
            LoadResult.Page(
                redditPosts ?: listOf(),
                null,
                listing?.after
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }
}