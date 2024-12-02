package com.example.athlitecsapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

                // Scaling progress to a percentage (assuming max is 30)
                val scaledProgress1 = if (progress1 == 1) 0 else (progress1 * 100) / 30
                val scaledProgress2 = if (progress2 == 1) 0 else (progress2 * 100) / 30
                val scaledProgress3 = if (progress3 == 1) 0 else (progress3 * 100) / 30
                val scaledProgress4 = if (progress4 == 1) 0 else (progress4 * 100) / 30
                val scaledProgress5 = if (progress5 == 1) 0 else (progress5 * 100) / 30
                val scaledProgress6 = if (progress6 == 1) 0 else (progress6 * 100) / 30
                val scaledProgress7 = if (progress7 == 1) 0 else (progress7 * 100) / 30

                // Binding values to UI components
                binding.progress1.progress = scaledProgress1
                binding.day1.text = "Day: $progress1"
                binding.progress1txt.text = "$scaledProgress1%"

                binding.progress2.progress = scaledProgress2
                binding.day2.text = "Day: $progress2"
                binding.progress2txt.text = "$scaledProgress2%"

                binding.progress3.progress = scaledProgress3
                binding.day3.text = "Day: $progress3"
                binding.progress3txt.text = "$scaledProgress3%"

                binding.progress4.progress = scaledProgress4
                binding.day4.text = "Day: $progress4"
                binding.progress4txt.text = "$scaledProgress4%"

                binding.progress5.progress = scaledProgress5
                binding.day5.text = "Day: $progress5"
                binding.progress5txt.text = "$scaledProgress5%"

                binding.progress6.progress = scaledProgress6
                binding.day6.text = "Day: $progress6"
                binding.progress6txt.text = "$scaledProgress6%"

                binding.progress7.progress = scaledProgress7
                binding.day7.text = "Day: $progress7"
                binding.progress7txt.text = "$scaledProgress7%"
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