package com.example.athlitecsapp.ui.notifications

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.athlitecsapp.R
import com.example.athlitecsapp.databinding.FragmentNotificationsBinding
import com.example.athlitecsapp.table.User
import com.example.athlitecsapp.util.DialogUtils
import com.example.athlitecsapp.util.HomeFactory
import com.example.athlitecsapp.util.SignUpFactory
import com.example.athlitecsapp.viewmodels.HomeViewModel
import com.example.athlitecsapp.viewmodels.SignUpModel
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity
import smartdevelop.ir.eram.showcaseviewlib.config.PointerType

class NotificationsFragment : Fragment() {
    private val sviewModel: SignUpModel by viewModels { SignUpFactory(requireContext()) }

    private var _binding: FragmentNotificationsBinding? = null
    private val viewModel: HomeViewModel by viewModels { HomeFactory(requireContext()) }
    private lateinit var loadingDialog: SweetAlertDialog
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    private fun onBoardingFinish(){
        val sharedPref = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", false)
        editor.apply()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences =
            requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val areGuidesShown = sharedPreferences.getBoolean("areGuidesShown", false)

        if (!areGuidesShown) {
            showAppBarGuide()
        }
        viewModel.getUserById(1) { user: User? ->
            if (user != null) {
                binding.fullName.text = user.email
            }
        }
        sviewModel.getAllStatus()
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

                // Function to handle progress update and completion state
                fun updateProgress(
                    progress: Int,
                    progressBar: ProgressBar,
                    progressLabel: TextView
                ) {
                    if (progress == 31) {
                        // If progress is 31, show finished and 100%
                        progressBar.progress = 100
                        progressLabel.text = "Finished"
                    } else {
                        // Otherwise, scale the progress
                        val scaledProgress = if (progress == 1) 0 else (progress * 100) / 30
                        progressBar.progress = scaledProgress
                        progressLabel.text = "$scaledProgress%"
                    }
                }

                // Update progress for all levels
                updateProgress(progress1, binding.progress100m, binding.progress100mLabel)
                updateProgress(progress2, binding.progress800m, binding.progress800mLabel)
                updateProgress(progress3, binding.progress3000m, binding.progress3000mLabel)
                updateProgress(progress4, binding.progress5k, binding.progress5kLabel)
                updateProgress(progress5, binding.progress200m, binding.progress200mLabel)
                updateProgress(progress6, binding.progress400m, binding.progress400mLabel)
                updateProgress(progress7, binding.progress1500m, binding.progress1500mLabel)
            }
        }

        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }

    }
    private fun showLogoutDialog() {
        DialogUtils.showWarningMessage(
            requireActivity(), "Logout", "Are you sure you want to Logout?"
        ) { sweetAlertDialog ->
            sweetAlertDialog.dismissWithAnimation()
            loadingDialog = DialogUtils.showLoading(requireActivity())
            loadingDialog.show()
            Handler().postDelayed({
                loadingDialog.dismiss()
                findNavController().navigate(R.id.signInFragment)
                onBoardingFinish()
            }, 2000)

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showAppBarGuide() {
        val toolbarGuide = GuideView.Builder(this@NotificationsFragment.requireContext())
            .setTitle("Info")
            .setContentText("Scroll down to see your progress and Logout Button.")
            .setGravity(Gravity.center)
            .setDismissType(DismissType.anywhere)
            .setPointerType(PointerType.circle)
            .setTargetView(binding.scroll)
            .setGuideListener { view: View ->

            }
            .build()
        val sharedPreferences =
            requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        toolbarGuide.show()
        sharedPreferences.edit().putBoolean("areGuidesShown", true).apply()
    }
}