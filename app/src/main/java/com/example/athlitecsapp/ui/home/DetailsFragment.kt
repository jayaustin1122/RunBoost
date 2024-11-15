package com.example.athlitecsapp.ui.home

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.athlitecsapp.databinding.FragmentDetailsBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private var player: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("title") ?: "No Title"
        val shortDesc = arguments?.getString("shortDesc") ?: "No Description"
        val image = arguments?.getInt("image")
        val video = arguments?.getInt("video")
        Log.d("DetailsFragment", "Video resource ID: $video")
        Log.d("DetailsFragment", "Image resource ID: $image")

        binding.textViewTitle.text = title
        binding.textViewShortDesc.text = shortDesc

        // Handling video
        if (video != 0 && video != null) {
            binding.image.visibility = View.GONE
            binding.playerView.visibility = View.VISIBLE
            initializePlayer(video)
        } else if (image != 0 && image != null) {
            binding.image.visibility = View.VISIBLE
            binding.playerView.visibility = View.GONE
            Glide.with(requireContext())
                .load(image)
                .into(binding.image)
        } else {
            binding.image.visibility = View.VISIBLE
            binding.playerView.visibility = View.GONE
            Toast.makeText(requireContext(), "No media to display", Toast.LENGTH_SHORT).show()
        }
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initializePlayer(videoResId: Int) {
        try {
            // Using videoResId to get the URI from raw resources
            val videoUri = Uri.parse("android.resource://${requireContext().packageName}/$videoResId")
            Log.d("DetailsFragment", "Video URI: $videoUri")

            player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
                binding.playerView.player = exoPlayer
                val mediaItem = MediaItem.fromUri(videoUri)
                exoPlayer.setMediaItem(mediaItem)

                // Prepare and start playback
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            }
        } catch (e: Exception) {
            Log.e("DetailsFragment", "Error initializing player: ${e.message}")
        }
    }


    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }
}
