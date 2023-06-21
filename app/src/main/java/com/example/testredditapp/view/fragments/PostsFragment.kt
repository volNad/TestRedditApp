package com.example.testredditapp.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testredditapp.R
import com.example.testredditapp.databinding.FragmentPostsBinding
import com.example.testredditapp.view.adapters.RedditPostsAdapter
import com.example.testredditapp.viewmodel.RedditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val viewModel: RedditViewModel by viewModels()
    private val redditPostsAdapter: RedditPostsAdapter = RedditPostsAdapter()
    private var _binding: FragmentPostsBinding? = null
    private val binding: FragmentPostsBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostsBinding.bind(view)
        setupUi()
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModel.posts.observe(viewLifecycleOwner) {pagingData ->
            redditPostsAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }
    }

    private fun setupUi() {
        binding.rvRedditList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = redditPostsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}