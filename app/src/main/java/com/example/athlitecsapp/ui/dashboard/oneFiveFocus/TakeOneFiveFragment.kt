package com.example.athlitecsapp.ui.dashboard.oneFiveFocus

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
import com.example.athlitecsapp.databinding.FragmentTakeFourHundredBinding
import com.example.athlitecsapp.databinding.FragmentTakeOneFiveBinding
import com.example.athlitecsapp.util.DialogUtils
import com.example.athlitecsapp.util.SignUpFactory
import com.example.athlitecsapp.viewmodels.SignUpModel


class TakeOneFiveFragment : Fragment() {
    private lateinit var binding : FragmentTakeOneFiveBinding
    private val viewModel: SignUpModel by viewModels { SignUpFactory(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTakeOneFiveBinding.inflate(layoutInflater)
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
                val progress = status.level7DayProgress
                binding.workoutProgressBar.progress = progress
                binding.dayDisplay.text = "Day: $progress"
                val (level, day) = when (progress) {
                    in 0..10 -> "BEGINNER" to progress
                    in 11..20 -> "INTERMEDIATE" to progress
                    in 21..30 -> "EXPERT" to progress
                    else -> "UNKNOWN" to 0 // Fallback
                }

                binding.btnStart.setOnClickListener {
                    Log.d("ListOf100Fragment", "Card clicked!")

                    // Use SweetAlertDialog for confirmation
                    DialogUtils.showWarningMessage(
                        activity = requireActivity(),
                        title = "Confirmation",
                        content = "Do you want to take this activity? Once started, it cannot be stopped!",
                        confirmListener = SweetAlertDialog.OnSweetClickListener {
                            // Pass arguments using a Bundle
                            val bundle = Bundle().apply {
                                putInt("day", status.level7DayProgress)
                                putString("level", level)
                                putString("scope", "1500m")
                            }

                            // Log the bundle values
                            Log.d(
                                "BundleContent",
                                "Day: ${bundle.getInt("day")}, Level: ${bundle.getString("level")}"
                            )

                            // Navigate to take100Fragment
                            findNavController().navigate(R.id.listsOneFiveFragment, bundle)

                            // Dismiss the dialog after confirming
                            it.dismissWithAnimation()
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