package com.example.testredditapp.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.testredditapp.R
import com.example.testredditapp.databinding.FragmentPostsBinding
import com.example.testredditapp.view.adapters.RedditPostsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val redditPostsAdapter: RedditPostsAdapter = RedditPostsAdapter()
    private var _binding: FragmentPostsBinding? = null
    private val binding: FragmentPostsBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostsBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}