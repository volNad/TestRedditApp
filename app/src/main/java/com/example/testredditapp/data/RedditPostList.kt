package com.example.testredditapp.data

data class RedditPostList(
    val children: List<PostContainer>,
    val after: String?,
    val before: String?
)