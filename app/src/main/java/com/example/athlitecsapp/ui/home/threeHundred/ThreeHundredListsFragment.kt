package com.example.athlitecsapp.ui.home.threeHundred

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.athlitecsapp.R
import com.example.athlitecsapp.adapter.VideoItemAdapter
import com.example.athlitecsapp.databinding.FragmentTakeThreeHundredBinding
import com.example.athlitecsapp.databinding.FragmentThreeHundredListsBinding
import com.example.athlitecsapp.util.DialogUtils
import com.example.athlitecsapp.util.HomeFactory
import com.example.athlitecsapp.util.SignUpFactory
import com.example.athlitecsapp.util.VideoItem
import com.example.athlitecsapp.viewmodels.HomeViewModel
import com.example.athlitecsapp.viewmodels.SignUpModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.launch


class ThreeHundredListsFragment : Fragment() {
    private lateinit var binding : FragmentThreeHundredListsBinding
    private var player: ExoPlayer? = null
    private var currentPlayer: SimpleExoPlayer? = null
    private val viewModel: HomeViewModel by viewModels { HomeFactory(requireContext()) }
    private val signUpviewModel: SignUpModel by viewModels { SignUpFactory(requireContext()) }
    private val videoList = mutableListOf<VideoItem>()
    private var currentPosition = 0  // Track the current item for timer and video logic
    private var isTimerRunning = false  // Flag to ensure the timer runs only once for item 1
    private var countDownTimer: CountDownTimer? = null // Declare the countdown timer
    private lateinit var loadingDialog: SweetAlertDialog
    private var day = 0
    private var scope = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThreeHundredListsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentPosition = savedInstanceState?.getInt("CURRENT_VIDEO_INDEX2") ?: getCurrentPositionFromPrefs()

        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }
        day = arguments?.getInt("day")!!
        scope = arguments?.getString("scope")!!
        val level = arguments?.getString("level")
        binding.dayTextView.text = "Day: $day"
        getWorksOut(day,scope) { workoutDescription ->
            videoList.addAll(
                listOf(

                    VideoItem(
                        R.drawable.strething,
                        "Stretching", """1. Roll Ankle In and Out
     - Purpose: To improve ankle mobility and flexibility.
     - How to: Gently rotate your foot inwards and outwards to loosen up the ankle joint.
    2. Forward and Back
     - Purpose: To stretch and activate the hamstrings and calves.
     - How to: Move your leg forward and back in a controlled manner to engage the
    lower body.
    3. Hip Flexion
     - Purpose: To improve flexibility and mobility in the hip joint.
     - How to: Lift your leg to a 90-degree angle, stretching the hip flexors.
    4. Hip Rotation
     - Purpose: To increase range of motion in the hips.
     - How to: Rotate your hip in a circular motion to loosen the joint.
    5. Leg Swing
     - Purpose: To activate the hip flexors and hamstrings.
     - How to: Swing your leg forward and backward to warm up the lower body.
    6. Side Swing
     - Purpose: To stretch the adductors and hip abductors.
     - How to: Swing your leg side to side, engaging the inner and outer thighs.
    7. Ankle Stretch
     - Purpose: To stretch the calves and improve ankle flexibility.
     - How to: Flex and point your toes to stretch the lower leg muscles.
    8. Arm Circles
     - Purpose: To warm up the shoulders and upper body.
     - How to: Perform small to large arm circles to increase shoulder mobility.
    9. Final Warmup
     - Purpose: To prepare the whole body for more intense activity.
     - How to: Perform a combination of dynamic movements to get your body ready for
    the workout.""", R.raw.stretching
                    ),
                    com.example.athlitecsapp.util.VideoItem(
                        R.drawable.running,
                        "Jogging",
                        "Take a Jog for About 5 mins",
                        null,
                        R.drawable.jogging
                    ),
                    com.example.athlitecsapp.util.VideoItem(
                        R.drawable.warm,
                        "Warm-Up", """1. Knee Chest
    - Definition: A dynamic stretch where you pull one knee toward your chest while
    balancing on the opposite leg, then switch legs.
    - Purpose: Improves flexibility in the glutes, hamstrings, and lower back while activating
    balance and core stability.
    2. Pull Quad
    - Definition: A standing stretch where you pull one foot behind you toward your glutes,
    holding the ankle or foot with your hand, and switch sides.
    -Purpose:Stretches the quadriceps and hip flexors to prepare the lower body for
    running or explosive movements.
    ---
    3. Open Hips
    - Definition: A movement where you lift your knee in front of you, rotate it outward to
    open the hips, and then return to the starting position.
    - Purpose: Enhances hip mobility, improves joint range of motion, and reduces the risk
    of groin and hip injuries.
    4. Open Hips (Side)
    - Definition: Similar to the standard "open hips" but involves swinging the leg out to
    the side in a wider range of motion.
    - Purpose: Targets lateral hip flexibility and strengthens abductors, preparing the body
    for lateral movements.
    5. Frankenstein
    - Definition: A dynamic stretch where you kick one leg straight out in front of you,
    reaching for your toes with the opposite hand, alternating sides.
    - Purpose: Stretches the hamstrings dynamically and warms up the hips and lower
    body.
    6. Lunge Walk
    - Definition: A walking movement where you step forward into a deep lunge position,
    alternating legs with each step.
    - Purpose: Stretches the hip flexors, quads, and glutes while also building lower-body
    strength and balance.
    7. Karaoke (Carioca)
    - Definition: A dynamic lateral movement where you cross one leg in front of and then
    behind the other while traveling sideways.
    - Purpose: Improves lateral agility, hip flexibility, and coordination, commonly used in
    sports drills. """, R.raw.warmup
                    ),
                    VideoItem(
                        R.drawable.workouts,
                        "Running Drills - Ankling",
                        getDescriptionByLevel(level!!),
                        R.raw.angkling
                    ),
                    VideoItem(
                        R.drawable.workout,
                        "Running Drills - A SKIPS",
                        getDescriptionByLevel(level!!),
                        R.raw.askips
                    ),
                    VideoItem(
                        R.drawable.abdominal,
                        "Running Drills - B SKIPS",
                        getDescriptionByLevel(level!!),
                        R.raw.bskips
                    ),
                    VideoItem(
                        R.drawable.sprint100,
                        "Running Drills - C SKIPS",
                        getDescriptionByLevel(level!!),
                        R.raw.cskips
                    ),
                    VideoItem(
                        R.drawable.ss,
                        "Running Drills - Butt Kicks",
                        getDescriptionByLevel(level!!),
                        R.raw.bkick
                    ),
                    VideoItem(
                        R.drawable.strething,
                        "Running Drills - High Knee",
                        getDescriptionByLevel(level!!),
                        R.raw.highknees
                    ),
                    VideoItem(
                        R.drawable.strething,
                        "Running Drills - High Knee Extension",
                        getDescriptionByLevel(level!!),
                        R.raw.highkneewithextension
                    ),
                    VideoItem(
                        R.drawable.strething,
                        "Running Drills -  Bounding",
                        getDescriptionByLevel(level!!),
                        R.raw.carioca
                    ),
                    VideoItem(
                        R.drawable.strething,
                        "Running Drills -  Russian",
                        getDescriptionByLevel(level!!),
                        R.raw.russian
                    ),
                    VideoItem(
                        R.drawable.strething,
                        "Running Drills -  Kangaroo",
                        getDescriptionByLevel(level!!),
                        R.raw.kangaroo
                    ),
                    VideoItem(
                        R.drawable.strething,
                        "Running Drills -  Carioca",
                        getDescriptionByLevel(level!!),
                        R.raw.carioca
                    ),
                    VideoItem(
                        R.drawable.strething,
                        "Main Workout",
                        workoutDescription,
                        null,
                        R.drawable.strething
                    ),
                    VideoItem(
                        R.drawable.strething,
                        "Core Exercises  -  Bridge",
                        getDescriptionByLevel2(level!!),
                        R.raw.bridge
                    ),
                    VideoItem(
                        R.drawable.strething,
                        "Core Exercises  -  Sit-Ups Half ",
                        getDescriptionByLevel2(level!!),
                        R.raw.situphalf
                    ),
                    VideoItem(
                        R.drawable.strething,
                        "Core Exercises  -  Sit-Ups Full ",
                        getDescriptionByLevel2(level!!),
                        R.raw.situpfull
                    ),
                    VideoItem(
                        R.drawable.strething,
                        "Core Exercises  -  Reverse Crunch ",
                        getDescriptionByLevel2(level!!),
                        R.raw.reversecrunch
                    ),
                    VideoItem(
                        R.drawable.strething,
                        "Core Exercises  -  Bicycle Scissors ",
                        getDescriptionByLevel2(level!!),
                        R.raw.bicycle
                    ),
                    VideoItem(
                        R.drawable.sprint100,
                        "Core Exercises  -  Vertical",
                        getDescriptionByLevel2(level!!),
                        R.raw.verticallegcrunch
                    ),
                    VideoItem(
                        R.drawable.bridge,
                        "Arm Power  -  Bridge",
                        getDescriptionByLevel3(level!!),
                        R.raw.bridge
                    ),
                    VideoItem(
                        R.drawable.backplank,
                        "Arm Power  -  Back Plank ",
                        getDescriptionByLevel3(level!!),
                        R.raw.ss
                    ),
                    VideoItem(
                        R.drawable.imgaw,
                        "Arm Power  - Side Plank ",
                        getDescriptionByLevel3(level!!),
                        R.raw.ss
                    ),
                    VideoItem(
                        R.drawable.kneepushup,
                        "Arm Power  -  Knee Push-Up ",
                        getDescriptionByLevel3(level!!),
                        R.raw.ss
                    ),
                    VideoItem(
                        R.drawable.arm,
                        "Arm Power  -  Push-Up Full  ",
                        getDescriptionByLevel3(level!!),
                        R.raw.ss
                    ),
                    VideoItem(
                        R.drawable.plank,
                        "Arm Power  -  Superman Plank  ",
                        getDescriptionByLevel3(level!!),
                        R.raw.ss
                    ),
                    VideoItem(
                        R.drawable.cooldown,
                        "Cooldown",
                        "(Static stretching, 10-15 counts each) ",
                        R.raw.cooldown
                    ),

                    )
            )
            Log.d("sss", day.toString())
            binding.recyclerViewTiniklingList.layoutManager = LinearLayoutManager(requireContext())

            // Create adapter and pass activePosition
            val adapter = VideoItemAdapter(videoList, { position ->
                // Handle item click
                showVideoForItem(position)
            }, currentPosition)
            if (videoList.isNotEmpty() && videoList[0].videoResId != null) {
                showVideoForItem(currentPosition)
            }
            binding.recyclerViewTiniklingList.adapter = adapter
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("CURRENT_VIDEO_INDEX1", currentPosition)
    }
    private fun saveCurrentPositionToPrefs() {
        val sharedPreferences = requireContext().getSharedPreferences("VideoPrefs2", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("CURRENT_VIDEO_INDEX2", currentPosition)
        editor.apply() // Save the value asynchronously
    }
    private fun getCurrentPositionFromPrefs(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("VideoPrefs2", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("CURRENT_VIDEO_INDEX2", 0)
    }
    private fun getWorksOut(day: Int?, scope: String, callback: (String) -> Unit) {
        if (day == null) {
            callback("No workout available.")
            return
        }

        lifecycleScope.launch {
            viewModel.fetchWorkout(scope,day) { workout ->
                val workoutDescription = buildString {
                    workout?.mainWorkOut?.let {
                        append("\nMain Workout: $it")
                    }
                    workout?.purpose?.let {
                        append("\nPurpose: $it")
                    }
                    workout?.tips?.let {
                        append("\nTips: $it")
                    }
                }
                Log.d("Workout", "Workout Description: $workoutDescription")
                callback(workoutDescription)  // Invoke the callback with the result
            }
        }
    }


    private fun showVideoForItem(position: Int) {
        // Show video for the selected item
        val videoItem = videoList[position]

        if (videoItem.videoResId != null) {
            // If there is a current player, stop and release it before creating a new one
            currentPlayer?.apply {
                stop()
                release()
            }

            // Hide image, show video
            binding.playerView.visibility = View.VISIBLE
            binding.imageView.visibility = View.GONE
            binding.timerTextView.visibility = View.GONE  // Hide timer if showing video

            // Initialize new player
            initializePlayer(videoItem.videoResId)

            binding.titleTextView.text = videoItem.title
            binding.descriptionTextView.text = videoItem.description
        } else {
            // Hide video, show image with timer
            binding.descriptionTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            binding.playerView.visibility = View.GONE
            binding.imageView.visibility = View.VISIBLE
            binding.timerTextView.visibility = View.VISIBLE

            videoItem.image?.let { binding.imageView.setImageResource(it) }

            binding.titleTextView.text = videoItem.title
            binding.descriptionTextView.text = videoItem.description
        }

        Log.d("Take100Fragment", "Selected position: $position")

        // Only start timer for position 1 and if not already running
        if (position == 1 && !isTimerRunning) {
            Log.d("Take100Fragment", "Starting countdown timer for position 1")
            startCountdownTimer()
        } else if (position == 14 && !isTimerRunning) {
            Log.d("Take100Fragment", "Starting countdown timer for position 1")
            startCountdownTimer()
        } else if (position == 28) {
            DialogUtils.showSuccessMessage(requireActivity(), "Success", "Day $day Completed!")
            signUpviewModel.updateLevel5(day++)
        }

        // Update the current position and scroll the RecyclerView to it
        currentPosition = position
        binding.recyclerViewTiniklingList.scrollToPosition(position)
    }

    private fun startCountdownTimer() {
        // Start countdown timer for 10 seconds (set to 5 minutes in this case)
        isTimerRunning = true
        binding.timerTextView.text = "05:00"

        countDownTimer = object : CountDownTimer(5 * 60 * 1000, 1000) { // 5 minutes = 5 * 60 * 1000 ms
            override fun onTick(millisUntilFinished: Long) {
                val minutesRemaining = millisUntilFinished / 1000 / 60
                val secondsRemaining = millisUntilFinished / 1000 % 60

                // Format the time to display minutes and seconds in mm:ss format
                val formattedTime = String.format("%02d:%02d", minutesRemaining, secondsRemaining)
                binding.timerTextView.text = formattedTime
            }

            override fun onFinish() {
                // Move to the next item when the timer finishes
                Log.d("Take100Fragment", "Timer finished, moving to next item")
                isTimerRunning = false
                moveToNextItem()
            }
        }.start()
    }

    private fun moveToNextItem() {
        loadingDialog = DialogUtils.showLoading(requireActivity())
        loadingDialog.show()

        // Delay the dismissal for 2 seconds after showing the progress dialog
        binding.recyclerViewTiniklingList.postDelayed({
            loadingDialog.dismiss()
            if (currentPosition + 1 < videoList.size) {
                currentPosition++
                saveCurrentPositionToPrefs()
                val nextItem = videoList[currentPosition]
                if (nextItem.videoResId != null) {
                    showVideoForItem(currentPosition)
                } else {
                    showVideoForItem(currentPosition)
                }
            }
        }, 5000)

    }

    private fun initializePlayer(videoResId: Int) {
        try {
            // If there's an existing player, release it before initializing a new one
            player?.apply {
                stop()
                release()
            }

            val videoUri = Uri.parse("android.resource://${requireContext().packageName}/$videoResId")
            player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
                binding.playerView.player = exoPlayer
                val mediaItem = MediaItem.fromUri(videoUri)
                exoPlayer.setMediaItem(mediaItem)

                // Add listener for playback state change
                exoPlayer.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        if (playbackState == Player.STATE_ENDED) {
                            // Show progress dialog while transitioning to next item
                            moveToNextItem()
                        }
                    }
                })

                // Prepare the player and start playback
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            }
        } catch (e: Exception) {
            Log.e("Take100Fragment", "Error initializing player: ${e.message}")
        }
    }



    override fun onStop() {
        super.onStop()
        releasePlayer()
        countDownTimer?.cancel() // Cancel the countdown timer if fragment is stopped
    }

    override fun onDestroyView() {
        super.onDestroyView()
        releasePlayer()
        countDownTimer?.cancel() // Cancel the countdown timer if the view is destroyed
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }


    fun getDescriptionByLevel(level: String): String {
        return when (level) {
            "BEGINNER" -> "BEGINNER, 1 set x 10 meter each"
            "INTERMEDIATE" -> "INTERMEDIATE, 2 sets x 20 meter each"
            "EXPERT" -> "EXPERT, 3 sets x 30 meter each"
            else -> "UNKNOWN LEVEL"
        }
    }

    fun getDescriptionByLevel2(level: String): String {
        return when (level) {
            "BEGINNER" -> "BEGINNER, 6 exercises, 1 set x 10 counts each"
            "INTERMEDIATE" -> "INTERMEDIATE, 6 exercises, 2 sets x 15 counts each"
            "EXPERT" -> "EXPERT, 6 exercises, 2 sets x 20 counts 15 each"
            else -> "UNKNOWN LEVEL"
        }
    }

    fun getDescriptionByLevel3(level: String): String {
        return when (level) {
            "BEGINNER" -> "BEGINNER, 7 exercises, 1 set x 10 counts each"
            "INTERMEDIATE" -> "INTERMEDIATE, 7 exercises, 2 sets x 15 counts each"
            "EXPERT" -> "EXPERT, 7 exercises, 2 sets x 20 counts each"
            else -> "UNKNOWN LEVEL"
        }
    }
}