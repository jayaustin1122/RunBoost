package com.example.athlitecsapp.ui.home.onehundred

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import com.example.athlitecsapp.databinding.FragmentListOf100Binding
import com.example.athlitecsapp.util.DialogUtils
import com.example.athlitecsapp.util.HomeFactory
import com.example.athlitecsapp.util.SignUpFactory
import com.example.athlitecsapp.viewmodels.HomeViewModel
import com.example.athlitecsapp.viewmodels.SharedViewModel
import com.example.athlitecsapp.viewmodels.SignUpModel


class ListOf100Fragment : Fragment() {
    private lateinit var binding: FragmentListOf100Binding
    private val viewModel: SignUpModel by viewModels { SignUpFactory(requireContext()) }
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListOf100Binding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = requireActivity().run {
            androidx.lifecycle.ViewModelProvider(this)[SharedViewModel::class.java]
        }

        viewModel.getAllStatus()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.statusLiveData.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                Log.d("ListOf100Fragment", "Status received: $status")
                val progress = status.level1DayProgress
                binding.workoutProgressBar.progress = progress

                if (progress >= 31) {
                    binding.workoutProgressBar.progress = 100
                    binding.dayDisplay.text = "Finished"
                    binding.btnStart.text = "Take Again?"

                } else {
                    val scaledProgress1 = if (progress == 1) 0 else (progress * 100) / 30
                    binding.dayDisplay.text = "Day ${progress}"
                    binding.workoutProgressBar.progress = scaledProgress1
                }

                val (level, day) = when (progress) {
                    in 0..10 -> "BEGINNER" to progress
                    in 11..20 -> "INTERMEDIATE" to progress
                    in 21..30 -> "EXPERT" to progress
                    else -> "UNKNOWN" to 0
                }

                binding.btnStart.setOnClickListener {
                    Log.d("ListOf100Fragment", "Card clicked!")

                    DialogUtils.showWarningMessage(
                        activity = requireActivity(),
                        title = "Confirmation",
                        content = "Do you want to start this activity? Once started, it cannot be stopped!",
                        confirmListener = SweetAlertDialog.OnSweetClickListener { dialog ->
                            val bundle = Bundle().apply {
                                putInt("day", status.level1DayProgress)
                                putString("level", level)
                                putString("scope", "100m")
                            }

                            Log.d(
                                "BundleContent",
                                "Day: ${bundle.getInt("day")}, Level: ${bundle.getString("level")}"
                            )

                            if (status.level1DayProgress >= 31) {
                                val resetBundle = Bundle().apply {
                                    putInt("day", 1)
                                    putString("level", level)
                                    putString("scope", "100m")
                                }

                                DialogUtils.showWarningMessage(
                                    activity = requireActivity(),
                                    title = "Confirmation",
                                    content = "Do you want to take this activity again? All progress will be reset!",
                                    confirmListener = SweetAlertDialog.OnSweetClickListener { resetDialog ->
                                        viewModel.updateLevel1(1)
                                        findNavController().navigate(R.id.take100Fragment, resetBundle)
                                        resetDialog.dismissWithAnimation()
                                        dialog.dismissWithAnimation()
                                    }
                                ).setCancelText("No")
                                    .setCancelClickListener { resetDialog ->
                                        resetDialog.dismissWithAnimation()
                                    }.show()

                            } else {
                                findNavController().navigate(R.id.take100Fragment, bundle)
                                dialog.dismissWithAnimation()
                            }
                        }
                    ).setCancelText("No")
                        .setCancelClickListener { dialog ->
                            dialog.dismissWithAnimation()
                        }.show()
                }

            } else {
                Log.d("ListOf100Fragment", "Status is null")
            }
        }
    }
}
