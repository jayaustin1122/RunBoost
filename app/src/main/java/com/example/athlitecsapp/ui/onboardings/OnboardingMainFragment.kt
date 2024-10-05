package com.example.athlitecsapp.ui.onboardings

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.athlitecsapp.R
import com.example.athlitecsapp.adapter.OnBoardingAdapter
import com.example.athlitecsapp.databinding.FragmentOnboardingMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingMainFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingMainBinding
    private lateinit var progressDialog : ProgressDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(this.requireContext())
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)
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
            findNavController().navigate(R.id.signInFragment)
        }

        // Handle Next button click
        binding.btnNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < fragmentList.size - 1) {
                binding.viewPager.currentItem = currentItem + 1
            } else {
                // If on the last page, you can navigate to another screen
                findNavController().navigate(R.id.signInFragment)
                onBoardingFinish()
            }
        }
    }
    private fun onBoardingFinish(){
        progressDialog.setMessage("Processing...")
        progressDialog.show()
        view?.postDelayed({
            val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("Finished", true)
            editor.apply()

            progressDialog.dismiss()
        }, 2000)
    }
}
