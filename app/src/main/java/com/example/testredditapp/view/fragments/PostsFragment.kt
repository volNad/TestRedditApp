package com.example.testredditapp.view.fragments

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testredditapp.R
import com.example.testredditapp.data.RedditPost
import com.example.testredditapp.databinding.FragmentPostsBinding
import com.example.testredditapp.utils.sdk29AndUp
import com.example.testredditapp.view.adapters.RedditPostsAdapter
import com.example.testredditapp.viewmodel.RedditViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts), RedditPostsAdapter.OnItemClickListener {

    private val viewModel: RedditViewModel by viewModels()
    private val redditPostsAdapter: RedditPostsAdapter = RedditPostsAdapter(this)
    private var _binding: FragmentPostsBinding? = null
    private val binding: FragmentPostsBinding get() = _binding!!
    private var writePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostsBinding.bind(view)
        setupUi()
        fetchPosts()
        initPermissionLauncher()
        updateOrRequestPermissions()
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

    private fun initPermissionLauncher() {
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            writePermissionGranted = permissions.values.all { it }
        }
    }



    override fun onItemClick(photo: RedditPost) {
        val action = PostsFragmentDirections.actionPostsFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }

    override fun onSaveImage(bitmap: Bitmap) {
        val displayName = "image_${System.currentTimeMillis()}"
        val success = savePhoto(displayName, bitmap)
        if (success) {
            Toast.makeText(requireContext(), "Image saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Error saving the image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateOrRequestPermissions() {
        val hasWritePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        writePermissionGranted = hasWritePermission || minSdk29

        val permissionsToRequest = mutableListOf<String>()
        if (!writePermissionGranted) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    private fun savePhoto(displayName: String, bmp: Bitmap): Boolean {
        val imageCollection = sdk29AndUp {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpeg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.WIDTH, bmp.width)
            put(MediaStore.Images.Media.HEIGHT, bmp.height)
        }

        val contentResolver = requireActivity().contentResolver

        return try {
            contentResolver.insert(imageCollection, contentValues)?.also { uri ->
                contentResolver.openOutputStream(uri).use { outputStream ->
                    if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)) {
                        throw IOException("Couldn't save the image")
                    }
                }
            } ?: throw IOException("Couldn't create MediaStore entry")
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}