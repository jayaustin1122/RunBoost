import android.app.Dialog
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.example.athlitecsapp.R
import android.view.WindowManager

class FullScreenVideoDialog : DialogFragment() {

    private var exoPlayer: ExoPlayer? = null
    private var previousOrientation: Int = 0
    private var videoUri: Uri? = null

    companion object {
        private const val ARG_VIDEO_URI = "videoUri"

        fun newInstance(videoUri: Uri): FullScreenVideoDialog {
            val fragment = FullScreenVideoDialog()
            val args = Bundle()
            args.putParcelable(ARG_VIDEO_URI, videoUri)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        videoUri = arguments?.getParcelable(ARG_VIDEO_URI)

        previousOrientation = activity?.requestedOrientation ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.fragment_full_screen_video_dialog)

        val playerView = dialog.findViewById<PlayerView>(R.id.playerView)

        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        exoPlayer = ExoPlayer.Builder(requireContext()).build().apply {
            playerView.player = this
            videoUri?.let {
                val mediaItem = MediaItem.fromUri(it)
                setMediaItem(mediaItem)
                prepare()
                play()
            }
        }

        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        activity?.requestedOrientation = previousOrientation
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        exoPlayer?.play()
    }
}
