package com.example.testredditapp.view.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testredditapp.R
import com.example.testredditapp.data.RedditPost
import com.example.testredditapp.databinding.ItemRedditPostPreviewBinding
import com.example.testredditapp.utils.ConverterHelpers.Companion.timeToNormal
import com.example.testredditapp.utils.DiffUtilCallBack

class RedditPostsAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<RedditPost, RedditPostsAdapter.ViewHolder>(DiffUtilCallBack()) {

    inner class ViewHolder(val binding: ItemRedditPostPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.rvPostImage.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        getItem(position)?.let { listener.onItemClick(it) }
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRedditPostPreviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { redditPost ->
            holder.binding.apply {
                rvAuthorValue.text = redditPost.author
                rvDateValue.text = timeToNormal(redditPost.createdUtc)
                rvCommentsValue.text = redditPost.numComments.toString()
            }
            Glide.with(holder.itemView)
                .load(redditPost.thumbnail)
                .apply(RequestOptions().placeholder(R.drawable.icon_no_image))
                .into(holder.binding.rvPostImage)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(photo: RedditPost)
    }

}

