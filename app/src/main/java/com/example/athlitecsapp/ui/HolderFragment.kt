package com.example.athlitecsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.athlitecsapp.R
import com.example.athlitecsapp.databinding.FragmentHolderBinding
import com.example.athlitecsapp.ui.dashboard.DashboardFragment
import com.example.athlitecsapp.ui.home.HomeFragment
import com.example.athlitecsapp.ui.notifications.NotificationsFragment
import nl.joery.animatedbottombar.AnimatedBottomBar

class HolderFragment : Fragment() {
    private var _binding: FragmentHolderBinding? = null
    private val binding get() = _binding!!

    // Fragment instances to cache them
    private val homeFragment = HomeFragment()
    private val dashboardFragment = DashboardFragment()
    private val notificationsFragment = NotificationsFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHolderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the initial fragment
        setCurrentFragment(homeFragment)

        // Set up the tab selection listener
        binding.navView.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                Log.d("bottom_bar", "Selected index: $newIndex, title: ${newTab.title}")

                // Switch between fragments based on the selected tab
                when (newIndex) {
                    0 -> setCurrentFragment(homeFragment)        // First tab: Home
                    1 -> setCurrentFragment(dashboardFragment)   // Second tab: Dashboard
                    2 -> setCurrentFragment(notificationsFragment) // Third tab: Notifications
                }
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                Log.d("bottom_bar", "Reselected index: $index, title: ${tab.title}")
                // Handle reselection if needed
            }
        })
    }

    private fun setCurrentFragment(fragment: Fragment) {
        // Check if the fragment is already added, if not, add it; otherwise, show it
        childFragmentManager.commit {
            childFragmentManager.fragments.forEach {
                hide(it) // Hide all fragments before showing the new one
            }

            if (!fragment.isAdded) {
                add(R.id.nav, fragment)
            }
            show(fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

