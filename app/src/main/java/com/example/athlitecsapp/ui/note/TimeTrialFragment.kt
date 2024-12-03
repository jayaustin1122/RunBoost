package com.example.athlitecsapp.ui.note

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.athlitecsapp.adapter.TimeTrialAdapter
import com.example.athlitecsapp.databinding.FragmentTimeTrialBinding
import com.example.athlitecsapp.table.TimeTrial
import com.example.athlitecsapp.util.DialogUtils
import com.example.athlitecsapp.util.HomeFactory
import com.example.athlitecsapp.viewmodels.HomeViewModel
import java.util.logging.Handler

class TimeTrialFragment : Fragment() {

    private lateinit var binding: FragmentTimeTrialBinding
    private var noteId: String? = null
    private val viewModel: HomeViewModel by viewModels { HomeFactory(requireContext()) }

    private var timer: CountDownTimer? = null
    private var isTimerRunning = false
    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private lateinit var loadingDialog: SweetAlertDialog
    private lateinit var timeTrialAdapter: TimeTrialAdapter
    private var timeTrials: MutableList<TimeTrial> = mutableListOf()

    private var progressAnimator: ObjectAnimator? = null // Add this to control progress bar animation
    private val TAG = "TimeTrialFragment" // For logging

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimeTrialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        // Initialize the RecyclerView and adapter
        timeTrialAdapter = TimeTrialAdapter(timeTrials)
        binding.recyclerViewTimeTrials.adapter = timeTrialAdapter
        binding.recyclerViewTimeTrials.layoutManager = LinearLayoutManager(requireContext())

        // Set the button to "Start Timer" initially
        binding.btnSaveTimeTrial.text = "Start Timer"

        // Set up the button listener immediately
        binding.btnSaveTimeTrial.setOnClickListener {
            if (isTimerRunning) {
                stopTimer()
                updateViewVisibility()
            } else {
                startTimer()
                updateViewVisibility()
            }
        }

        arguments?.let {
            noteId = it.getString("noteId")
        }

        loadingDialog = DialogUtils.showLoading(requireActivity())
        loadingDialog.show()

        android.os.Handler().postDelayed({
            loadingDialog.dismiss()
            noteId?.let { id ->
                viewModel.getNoteById(id.toLong()) { note ->
                    note?.let {
                        timeTrials.clear()
                        timeTrials.addAll(note.timeTrials)
                        timeTrialAdapter.notifyDataSetChanged()
                        updateViewVisibility()
                    }
                }
            }
        }, 2000)

}

    private fun updateViewVisibility() {

        if (timeTrials.isEmpty()) {
            binding.recyclerViewTimeTrials.visibility = View.GONE
            binding.noTrial.visibility = View.VISIBLE
            binding.imgEmpty.visibility = View.VISIBLE
        } else {
            binding.recyclerViewTimeTrials.visibility = View.VISIBLE
            binding.imgEmpty.visibility = View.GONE
            binding.noTrial.visibility = View.GONE
        }
    }

    private fun startTimer() {
        isTimerRunning = true
        startTime = System.currentTimeMillis()
        elapsedTime = 0
        updateViewVisibility()
        // Reset progress bar to 0
        binding.progressBarCircular.progress = 0

        // Animate progress bar from 0 to 100 over 5 minutes (300 seconds)
        val durationMillis = 300_000L // 5 minutes
        progressAnimator = ObjectAnimator.ofInt(binding.progressBarCircular, "progress", 0, 100)
        progressAnimator?.duration = durationMillis
        progressAnimator?.start()

        // Start the CountDownTimer to update the time display
        timer = object : CountDownTimer(durationMillis, 100) { // Update every 100ms for more precision
            override fun onTick(millisUntilFinished: Long) {
                elapsedTime = System.currentTimeMillis() - startTime
                val totalSeconds = elapsedTime / 1000
                val totalMillis = (elapsedTime % 1000).toInt() // Get the remaining milliseconds

                binding.tvTimer.text = formatTime(totalSeconds, totalMillis)

                // The ObjectAnimator is handling the progress, so no need to manually update the progress
            }

            override fun onFinish() {
                binding.tvTimer.text = "00:00:00"
                binding.progressBarCircular.progress = 100 // Ensure progress is set to 100 when timer finishes
            }
        }.start()

        binding.btnSaveTimeTrial.text = "Stop Timer"
    }

    private fun formatTime(seconds: Long, milliseconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60

        // Format the time as MM:SS:SSS (minutes:seconds:milliseconds)
        return String.format("%02d:%02d:%02d", minutes, remainingSeconds, milliseconds)
    }

    private fun stopTimer() {
        isTimerRunning = false
        timer?.cancel()
        updateViewVisibility()

        // Cancel the progress animation
        progressAnimator?.cancel()

        // Reset timer and progress bar
        binding.tvTimer.text = "00:00:00"
        binding.progressBarCircular.progress = 0

        // Save the time trial
        val trialNumber = timeTrials.size + 1
        val timeInSeconds = elapsedTime / 1000.0
        val date = System.currentTimeMillis().toString()

        val timeTrial = TimeTrial(trialNumber, timeInSeconds, date)
        timeTrials.add(timeTrial)
        timeTrialAdapter.notifyItemInserted(timeTrials.size - 1)
        binding.recyclerViewTimeTrials.scrollToPosition(timeTrials.size - 1) // Smooth scroll to the latest entry

        // Update the Note
        noteId?.let {
            viewModel.updateNoteByid(it.toLong(), timeTrials)
        }

        binding.btnSaveTimeTrial.text = "Start Timer"
    }
}
