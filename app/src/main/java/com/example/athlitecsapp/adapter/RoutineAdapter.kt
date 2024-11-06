import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.athlitecsapp.dao.StatusDao
import com.example.athlitecsapp.database.RunBoostDatabase
import com.example.athlitecsapp.databinding.ItemViewsListBinding
import com.example.athlitecsapp.model.Routine
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class RoutineAdapter(
    private val context: Context,
    var list: MutableList<Routine>
) : RecyclerView.Adapter<RoutineAdapter.ViewHolderAdapter>() {

    private var expandedPosition: Int = RecyclerView.NO_POSITION
    private var exoPlayer: ExoPlayer? = null

    inner class ViewHolderAdapter(val binding: ItemViewsListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemViewsListBinding.inflate(inflater, parent, false)
        return ViewHolderAdapter(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderAdapter, position: Int) {
        val currentPosition = holder.adapterPosition
        val data = list[currentPosition]

        holder.binding.apply {
            titleText.text = data.title
            workoutVideo.visibility = if (data.open) View.VISIBLE else View.GONE

            // Check if the item is expandable and should be played
            if (data.open ) {
                // Initialize ExoPlayer if it's not already
                exoPlayer = ExoPlayer.Builder(context).build()

                // Set up the PlayerView
                workoutVideo.player = exoPlayer

                // Get the video URI
                val videoUri = Uri.parse("android.resource://${context.packageName}/${data.videoId}")
                val mediaItem = MediaItem.fromUri(videoUri)
                exoPlayer?.setMediaItem(mediaItem)

                // Prepare and start the player
                exoPlayer?.prepare()
                exoPlayer?.play()

                // Hide the play button
                imgPlay.visibility = View.GONE
                workoutVideo.visibility = View.VISIBLE

                // Set up the ExoPlayer listener
                exoPlayer?.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        when (playbackState) {
                            ExoPlayer.STATE_READY -> {
                                // Player is ready
                            }
                            ExoPlayer.STATE_ENDED -> {

                                notifyItemChanged(currentPosition)

                                // Update the next item to be expandable if possible
                                if (currentPosition + 1 < list.size) {
                                    list[currentPosition + 1].isExpandable = true
                                    (context as AppCompatActivity).lifecycleScope.launch {
                                      //  updateStatusInRoom(currentPosition.toLong() + 1, true)
                                    }
                                    notifyItemChanged(currentPosition + 1)
                                }


                            }
                        }
                    }

                    override fun onPlayerError(error: PlaybackException) {
                        // Handle the player error, show a message, etc.
                        Toast.makeText(context, "Error playing video: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else if (!data.open) {
                // Show a toast if the item is locked
                imgPlay.visibility = View.VISIBLE
                workoutVideo.visibility = View.GONE
                if (currentPosition != 0 && !list[currentPosition - 1].isPlayed) {
                    // The current item is locked because the previous item isn't played
                    Toast.makeText(context, "Currently locked, please wait for previous item", Toast.LENGTH_SHORT).show()
                }
            }

            // Handle item clicks
            root.setOnClickListener {
                if (currentPosition == 0 || list[currentPosition - 1].isPlayed) {
                    val previousExpandedPosition = expandedPosition
                    if (previousExpandedPosition != RecyclerView.NO_POSITION) {
                        list[previousExpandedPosition].isExpandable = false
                        notifyItemChanged(previousExpandedPosition)
                    }

                    expandedPosition = currentPosition
                    data.isExpandable = true
                    notifyItemChanged(currentPosition)
                } else {
                    // Show toast if the item is locked
                    Toast.makeText(context, "Currently locked, please wait for previous item", Toast.LENGTH_SHORT).show()
                }
            }

            workoutVideo.setOnClickListener {
                // Use newInstance method to pass the Uri as an argument to the Fragment
                val videoUri = Uri.parse("android.resource://${context.packageName}/${data.videoId}")
                val fullScreenDialog = FullScreenVideoDialog.newInstance(videoUri)

                // Show the full-screen video dialog
                fullScreenDialog.show((context as AppCompatActivity).supportFragmentManager, "FullScreenVideoDialog")
            }
        }
    }

    override fun getItemCount(): Int = list.size

    // Release ExoPlayer when the view is detached
    override fun onViewRecycled(holder: ViewHolderAdapter) {
        super.onViewRecycled(holder)
        holder.binding.workoutVideo.player?.release()
    }


}

