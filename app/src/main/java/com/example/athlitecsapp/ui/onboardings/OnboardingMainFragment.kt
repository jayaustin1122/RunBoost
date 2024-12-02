package com.example.athlitecsapp.ui.onboardings

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.athlitecsapp.R
import com.example.athlitecsapp.adapter.OnBoardingAdapter
import com.example.athlitecsapp.databinding.FragmentOnboardingMainBinding
import com.example.athlitecsapp.util.DialogUtils
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingMainFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingMainBinding
    private lateinit var loadingDialog: SweetAlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf<Fragment>(
            OnBoardingOneFragment(),
            OnBoardingTwoFragment(),
            OnBoardingThreeFragment(),
            OnBoardingFourFragment()
        )

        val adapter = OnBoardingAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter

        // Link TabLayout with ViewPager2 for dots indicator
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        binding.btnSkip.setOnClickListener {
            loadingDialog = DialogUtils.showLoading(requireActivity())
            loadingDialog.show()
            findNavController().navigate(R.id.signInFragment)
            onBoardingFinish()
        }

        // Handle Next button click
        binding.btnNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < fragmentList.size - 1) {
                binding.viewPager.currentItem = currentItem + 1
            } else {
                loadingDialog = DialogUtils.showLoading(requireActivity())
                loadingDialog.show()
                // If on the last page, you can navigate to another screen
                findNavController().navigate(R.id.signInFragment)
                onBoardingFinish()
            }
        }
    }
    private fun onBoardingFinish(){
        view?.postDelayed({
            val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("Finished", true)
            editor.apply()

            loadingDialog.dismiss()
        }, 2000)
    }
}
