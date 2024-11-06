package com.example.athlitecsapp.ui.home

import RoutineAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.athlitecsapp.R
import com.example.athlitecsapp.databinding.FragmentListOFRoutinesBinding
import com.example.athlitecsapp.model.Routine

class ListOFRoutinesFragment : Fragment() {
    private var _binding : FragmentListOFRoutinesBinding? = null
    private val binding get() = _binding!!

    var lists = mutableListOf<Routine>(
        Routine("Sample","sample",R.raw.ss,0,false,true,true),
        Routine("Sample","sample",R.raw.ss,1,false,false,false),
        Routine("Sample","sample",R.raw.ss,2,false,false,false),
        Routine("Sample","sample",R.raw.ss,3,false,false,false),
        Routine("Sample","sample",R.raw.ss,4,false,false,false)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOFRoutinesBinding.inflate(layoutInflater)
        val root : View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val routineAdapter = RoutineAdapter(requireContext(), lists)
        binding.recycler.adapter = routineAdapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        binding.apply {
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}
