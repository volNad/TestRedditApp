package com.example.testredditapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.testredditapp.data.RedditPost

class DiffUtilCallBack : DiffUtil.ItemCallback<RedditPost>() {
    override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
        return oldItem.id == newItem.id
                && oldItem.score == newItem.score
                && oldItem.numComments == newItem.numComments
    }
}