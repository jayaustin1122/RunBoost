package com.example.athlitecsapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.athlitecsapp.R
import com.example.athlitecsapp.adapter.RoutineAdapter
import com.example.athlitecsapp.databinding.FragmentCategoriesListsBinding
import com.example.athlitecsapp.util.FragmentNavigationUtils



class CategoriesListsFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesListsBinding
    private lateinit var categoryTitle: String
    private val viewModel: HomeViewModel by viewModels { HomeFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryTitle = arguments?.getString("category_title") ?: "Default Title"
        binding.textViewCategoryTitle.text = categoryTitle

        viewModel.getTiniklingByCategory(categoryTitle) { tiniklingList ->
            if (tiniklingList.isEmpty()) {
                // Show the "no data" image and hide RecyclerView
                binding.recyclerViewTiniklingList.visibility = View.GONE
                binding.noDataImage.visibility = View.VISIBLE
            } else {
                // Hide the "no data" image and show RecyclerView with data
                binding.recyclerViewTiniklingList.visibility = View.VISIBLE
                binding.noDataImage.visibility = View.GONE

                val adapter = RoutineAdapter(tiniklingList) { tinikling, position ->

                        val bundle = Bundle().apply {
                            putString("title", tinikling.title)
                            putString("shortDesc", tinikling.description)
                            tinikling.image?.let {
                                putInt("image", it)
                            }
                            tinikling.video?.let {
                                putInt("video", it)
                            }
                        }
                        // Use FragmentNavigationUtils to navigate with animation
                        FragmentNavigationUtils.navigateWithAnimation(
                            R.id.detailsFragment, // Destination Fragment
                            findNavController(),   // NavController
                            bundle                 // Bundle with data
                        )
                    }


                // Set up the RecyclerView
                binding.recyclerViewTiniklingList.adapter = adapter
                binding.recyclerViewTiniklingList.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        binding.backButton.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("initialTabIndex", 0)
            }
            // Use FragmentNavigationUtils to navigate with animation
            FragmentNavigationUtils.navigateWithAnimation(
                R.id.holderFragment,    // Destination Fragment
                findNavController(),    // NavController
                bundle                  // Bundle with data
            )
        }
    }

}
