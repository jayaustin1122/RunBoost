package com.example.athlitecsapp.ui.dashboard.fiveK

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.athlitecsapp.R
import com.example.athlitecsapp.databinding.FragmentTakeFiveKBinding
import com.example.athlitecsapp.util.DialogUtils
import com.example.athlitecsapp.util.SignUpFactory
import com.example.athlitecsapp.viewmodels.SignUpModel


class TakeFiveKFragment : Fragment() {
    private lateinit var binding: FragmentTakeFiveKBinding
    private val viewModel: SignUpModel by viewModels { SignUpFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTakeFiveKBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllStatus()
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        viewModel.statusLiveData.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                Log.d("ListOf100Fragment", "Status received: $status")
                val progress = status.level4DayProgress
                binding.workoutProgressBar.progress = progress
                if (progress >= 31){
                    binding.workoutProgressBar.progress = 100
                    binding.dayDisplay.text = "Finished"
                    binding.btnStart.text = "Take Again?"

                }
                else{
                    val scaledProgress1 = if (progress == 1) 0 else (progress * 100) / 30
                    binding.dayDisplay.text = "Day ${progress}"
                    binding.workoutProgressBar.progress = scaledProgress1
                }

                binding.dayDisplay.text = "Day: $progress"
                val (level, day) = when (progress) {
                    in 0..10 -> "BEGINNER" to progress
                    in 11..20 -> "INTERMEDIATE" to progress
                    in 21..30 -> "EXPERT" to progress
                    else -> "UNKNOWN" to 0 // Fallback
                }

                binding.btnStart.setOnClickListener {
                    Log.d("ListOf100Fragment", "Card clicked!")

                    DialogUtils.showWarningMessage(
                        activity = requireActivity(),
                        title = "Confirmation",
                        content = "Do you want to start this activity? Once started, it cannot be stopped!",
                        confirmListener = SweetAlertDialog.OnSweetClickListener { dialog ->
                            val bundle = Bundle().apply {
                                putInt("day", status.level4DayProgress)
                                putString("level", level)
                                putString("scope", "5000m")
                            }

                            Log.d(
                                "BundleContent",
                                "Day: ${bundle.getInt("day")}, Level: ${bundle.getString("level")}"
                            )

                            // Check if progress is 31 or more (indicating completion)
                            if (status.level4DayProgress >= 31) {
                                val resetBundle = Bundle().apply {
                                    putInt("day", 1)
                                    putString("level", level)
                                    putString("scope", "5000m")
                                }

                                // Show reset confirmation dialog
                                DialogUtils.showWarningMessage(
                                    activity = requireActivity(),
                                    title = "Confirmation",
                                    content = "Do you want to take this activity again? All progress will be reset!",
                                    confirmListener = SweetAlertDialog.OnSweetClickListener { resetDialog ->
                                        // Reset progress and navigate to the reset activity
                                        viewModel.updateLevel4(1)
                                        findNavController().navigate(R.id.listsTakeFiveKFragment, resetBundle)
                                        // Dismiss both dialogs
                                        resetDialog.dismissWithAnimation()
                                        dialog.dismissWithAnimation()
                                    }
                                ).setCancelText("No")
                                    .setCancelClickListener { resetDialog ->
                                        // Dismiss only the reset dialog if "No" is clicked
                                        resetDialog.dismissWithAnimation()
                                    }.show()
                            } else {
                                // If progress is less than 31, navigate directly without resetting progress
                                findNavController().navigate(R.id.listsTakeFiveKFragment, bundle)
                                // Dismiss the initial dialog
                                dialog.dismissWithAnimation()
                            }
                        }
                    ).setCancelText("No")
                        .setCancelClickListener { dialog ->
                            // Dismiss the initial dialog if "No" is clicked
                            dialog.dismissWithAnimation()
                        }.show()
                }

            } else {
                Log.d("ListOf100Fragment", "Status is null")
            }
        }
    }
}