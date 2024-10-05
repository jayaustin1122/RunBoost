package com.example.athlitecsapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.athlitecsapp.R
import com.example.athlitecsapp.adapter.RoutineAdapter
import com.example.athlitecsapp.databinding.FragmentListOFRoutinesBinding
import com.example.athlitecsapp.model.Routine


class ListOFRoutinesFragment : Fragment() {
    private var _binding : FragmentListOFRoutinesBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: RoutineAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOFRoutinesBinding.inflate(layoutInflater)
        val root : View = binding.root
        return root
    }
    var lists = mutableListOf<Routine>(
        Routine("Sample","sample",R.drawable.splash),
        Routine("Sample","sample",R.drawable.workout),
        Routine("Sample","sample",R.drawable.create_acc),
        Routine("Sample","sample",R.drawable.onboardingone),
        Routine("Sample","sample",R.drawable.onboardingthree),

    )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RoutineAdapter(lists)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        lists.sortBy {
            it.title
        }
        binding.apply {
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

}