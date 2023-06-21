package com.example.testredditapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RedditPost(
    val author: String,
    @SerializedName("created_utc")
    val createdUtc: Long,
    val id: String,
    @SerializedName("num_comments")
    val numComments: Int,
    val thumbnail: String,
    val url: String,
    @SerializedName("score")
    val score: Int
) : Parcelable