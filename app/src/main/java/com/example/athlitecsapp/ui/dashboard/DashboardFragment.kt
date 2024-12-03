package com.example.athlitecsapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.athlitecsapp.R
import com.example.athlitecsapp.databinding.FragmentDashboardBinding
import com.example.athlitecsapp.util.DialogUtils
import com.example.athlitecsapp.util.SignUpFactory
import com.example.athlitecsapp.viewmodels.DashboardViewModel
import com.example.athlitecsapp.viewmodels.SignUpModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var loadingDialog: SweetAlertDialog
    private val sviewModel: SignUpModel by viewModels { SignUpFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sviewModel.getAllStatus()
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        sviewModel.statusLiveData.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                // Progress for levels 1 to 7
                val progress1 = status.level1DayProgress
                val progress2 = status.level2DayProgress
                val progress3 = status.level3DayProgress
                val progress4 = status.level4DayProgress
                val progress5 = status.level5DayProgress
                val progress6 = status.level6DayProgress
                val progress7 = status.level7DayProgress

                // Function to handle scaling and display logic
                fun setProgress(
                    progress: Int,
                    progressBar: ProgressBar,
                    dayText: TextView,
                    progressText: TextView
                ) {
                    if (progress == 31) {
                        // If progress is 31, show finished and 100%
                        progressBar.progress = 100
                        dayText.text = "Finished"
                        progressText.text = "100%"
                    } else {
                        // Otherwise, scale progress and display normally
                        val scaledProgress = if (progress == 1) 0 else (progress * 100) / 30
                        progressBar.progress = scaledProgress
                        dayText.text = "Day: $progress"
                        progressText.text = "$scaledProgress%"
                    }
                }

                // Apply the function to all progress levels
                setProgress(progress1, binding.progress1, binding.day1, binding.progress1txt)
                setProgress(progress2, binding.progress2, binding.day2, binding.progress2txt)
                setProgress(progress3, binding.progress3, binding.day3, binding.progress3txt)
                setProgress(progress4, binding.progress4, binding.day4, binding.progress4txt)
                setProgress(progress5, binding.progress5, binding.day5, binding.progress5txt)
                setProgress(progress6, binding.progress6, binding.day6, binding.progress6txt)
                setProgress(progress7, binding.progress7, binding.day7, binding.progress7txt)
            }
        }


        binding.cardSprint100m.setOnClickListener {
            loadingDialog = DialogUtils.showLoading(requireActivity())
            loadingDialog.show()

            lifecycleScope.launch(Dispatchers.IO) {
                // Add a 2-second delay
                delay(2000)

                withContext(Dispatchers.Main) {
                    // Navigate and dismiss the loading dialog on the main thread
                    findNavController().navigate(R.id.listOf100Fragment)
                    loadingDialog.dismiss()
                }
            }
        }
        binding.card800.setOnClickListener {
            loadingDialog = DialogUtils.showLoading(requireActivity())
            loadingDialog.show()

            lifecycleScope.launch(Dispatchers.IO) {
                // Add a 2-second delay
                delay(2000)

                withContext(Dispatchers.Main) {
                    // Navigate and dismiss the loading dialog on the main thread
                    findNavController().navigate(R.id.eightHundredFragment)
                    loadingDialog.dismiss()
                }
            }
        }
        binding.cardSprint3000.setOnClickListener {
            loadingDialog = DialogUtils.showLoading(requireActivity())
            loadingDialog.show()

            lifecycleScope.launch(Dispatchers.IO) {
                delay(2000)

                withContext(Dispatchers.Main) {
                    // Navigate and dismiss the loading dialog on the main thread
                    findNavController().navigate(R.id.takeThreeHundredFragment)
                    loadingDialog.dismiss()
                }
            }
        }
        binding.cardSprint5k.setOnClickListener {
            loadingDialog = DialogUtils.showLoading(requireActivity())
            loadingDialog.show()

            lifecycleScope.launch(Dispatchers.IO) {
                delay(2000)

                withContext(Dispatchers.Main) {
                    // Navigate and dismiss the loading dialog on the main thread
                    findNavController().navigate(R.id.takeFiveKFragment)
                    loadingDialog.dismiss()
                }
            }
        }
        binding.cardSprint200mFocus.setOnClickListener {
            loadingDialog = DialogUtils.showLoading(requireActivity())
            loadingDialog.show()

            lifecycleScope.launch(Dispatchers.IO) {
                delay(2000)

                withContext(Dispatchers.Main) {
                    // Navigate and dismiss the loading dialog on the main thread
                    findNavController().navigate(R.id.takeTwoHundredFragment)
                    loadingDialog.dismiss()
                }
            }
        }
        binding.cardSprint400mFocus.setOnClickListener {
            loadingDialog = DialogUtils.showLoading(requireActivity())
            loadingDialog.show()

            lifecycleScope.launch(Dispatchers.IO) {
                delay(2000)

                withContext(Dispatchers.Main) {
                    // Navigate and dismiss the loading dialog on the main thread
                    findNavController().navigate(R.id.takeFourHundredFragment)
                    loadingDialog.dismiss()
                }
            }
        }
        binding.cardSprint1500mFocus.setOnClickListener {
            loadingDialog = DialogUtils.showLoading(requireActivity())
            loadingDialog.show()

            lifecycleScope.launch(Dispatchers.IO) {
                delay(2000)

                withContext(Dispatchers.Main) {
                    // Navigate and dismiss the loading dialog on the main thread
                    findNavController().navigate(R.id.takeOneFiveFragment)
                    loadingDialog.dismiss()
                }
            }
        }

    }
}