package com.example.athlitecsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.athlitecsapp.R
import com.example.athlitecsapp.adapter.CategoryAdapter
import com.example.athlitecsapp.adapter.TiniklingVideoAdapter
import com.example.athlitecsapp.databinding.FragmentHomeBinding
import com.example.athlitecsapp.table.User
import com.example.athlitecsapp.util.CategoryList
import com.example.athlitecsapp.util.DialogUtils
import com.example.athlitecsapp.util.FragmentNavigationUtils
import com.example.athlitecsapp.util.HomeFactory
import com.example.athlitecsapp.util.SignUpFactory
import com.example.athlitecsapp.viewmodels.HomeViewModel
import com.example.athlitecsapp.viewmodels.SignUpModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var loadingDialog: SweetAlertDialog
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels { HomeFactory(requireContext()) }
    private val sviewModel: SignUpModel by viewModels { SignUpFactory(requireContext()) }

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
        sviewModel.getAllStatus()
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
        binding.seAll.setOnClickListener {
            findNavController().navigate(R.id.dashboard)
        }
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.adapter = adapter

        viewModel.getUserById(1) { user: User? ->
            if (user != null) {
                binding.greetingText.text = "Hello, ${user.email}!"
            }
        }

        binding.tingnanLahatNgCategories.setOnClickListener {
            val bundle = Bundle().apply {
                putString("from", "category")
            }
            // Use FragmentNavigationUtils to navigate with animation
//            FragmentNavigationUtils.navigateWithAnimation(
//                R.id.allCategoriesFragment,  // Destination Fragment
//                findNavController(),         // NavController for navigation
//                bundle                      // Bundle with data
//            )
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
        sviewModel.statusLiveData.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                val progress = status.level1DayProgress
                val progress2 = status.level2DayProgress
                val progress3 = status.level3DayProgress
                val scaledProgress = if (progress == 1) 0 else (progress * 100) / 30
                val scaledProgress2 = if (progress2 == 1) 0 else (progress2 * 100) / 30
                val scaledProgress3 = if (progress3 == 1) 0 else (progress3 * 100) / 30

                binding.progress1.progress = scaledProgress
                binding.day.text = "Day: $progress"
                binding.progresstxt1.text = "$scaledProgress%"

                binding.progress2.progress = scaledProgress2
                binding.day2.text = "Day: $progress2"
                binding.progress2txt.text = "$scaledProgress2%"

                binding.progress3.progress = scaledProgress3
                binding.day3.text = "Day: $progress3"
                binding.progress3txt.text = "$scaledProgress3%"
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
                // Add a 2-second delay
                delay(2000)

                withContext(Dispatchers.Main) {
                    // Navigate and dismiss the loading dialog on the main thread
                    findNavController().navigate(R.id.takeThreeHundredFragment)
                    loadingDialog.dismiss()
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}