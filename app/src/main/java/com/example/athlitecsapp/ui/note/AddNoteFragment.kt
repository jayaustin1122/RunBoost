package com.example.athlitecsapp.ui.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.athlitecsapp.R
import com.example.athlitecsapp.databinding.FragmentAddNoteBinding
import com.example.athlitecsapp.table.Note
import com.example.athlitecsapp.util.HomeFactory
import com.example.athlitecsapp.viewmodels.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
class AddNoteFragment(private val onNoteAdded: (Note) -> Unit) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddNoteBinding
    private val viewModel: HomeViewModel by viewModels { HomeFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveExercise.setOnClickListener {
            val exerciseName = binding.etExerciseType.text.toString()
            if (exerciseName.isNotEmpty()) {
                val newNote = Note(exerciseName, emptyList())
                viewModel.insertNote(newNote)
                onNoteAdded(newNote)
                dismiss()
            }
        }
    }
}
