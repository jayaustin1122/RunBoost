package com.example.athlitecsapp.util

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

object PlayerUtils {

    fun initializePlayer(context: Context, playerView: com.google.android.exoplayer2.ui.PlayerView, videoResId: Int, onVideoEnd: () -> Unit) {
        try {
            val videoUri = Uri.parse("android.resource://${context.packageName}/$videoResId")
            Log.d("PlayerUtils", "Video URI: $videoUri")

            val player = ExoPlayer.Builder(context).build().apply {
                playerView.player = this
                val mediaItem = MediaItem.fromUri(videoUri)
                setMediaItem(mediaItem)

                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        if (playbackState == Player.STATE_ENDED) {
                            onVideoEnd()  // Call the callback when the video ends
                        }
                    }
                })

                // Prepare and start playback
                prepare()
                playWhenReady = true
            }

        } catch (e: Exception) {
            Log.e("PlayerUtils", "Error initializing player: ${e.message}")
        }
    }
}
