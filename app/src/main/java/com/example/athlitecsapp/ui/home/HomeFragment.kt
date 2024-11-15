package com.example.athlitecsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.athlitecsapp.R
import com.example.athlitecsapp.adapter.CategoryAdapter
import com.example.athlitecsapp.databinding.FragmentHomeBinding
import com.example.athlitecsapp.table.User
import com.example.athlitecsapp.util.CategoryList
import com.example.athlitecsapp.util.FragmentNavigationUtils

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels { HomeFactory(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryList = CategoryList.categories
        val adapter = CategoryAdapter(requireContext(), categoryList) { categoryTitle ->
            val bundle = Bundle().apply {
                putString("category_title", categoryTitle)
            }
            // Use FragmentNavigationUtils to navigate with animation
            FragmentNavigationUtils.navigateWithAnimation(
                R.id.categoriesListsFragment,  // Destination Fragment
                findNavController(),           // NavController for navigation
                bundle                        // Bundle with data
            )
        }

        viewModel.getUserById(1) { user: User? ->
            if (user != null) {
                binding.greetingText.text = "Hello, ${user.email}!"
            }
        }
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.adapter = adapter


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}