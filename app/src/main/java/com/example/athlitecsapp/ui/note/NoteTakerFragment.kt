package com.example.athlitecsapp.ui.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.athlitecsapp.R
import com.example.athlitecsapp.adapter.NoteAdapter
import com.example.athlitecsapp.databinding.FragmentNoteTakerBinding
import com.example.athlitecsapp.table.Note
import com.example.athlitecsapp.util.HomeFactory
import com.example.athlitecsapp.viewmodels.HomeViewModel
class NoteTakerFragment : Fragment() {
    private lateinit var binding: FragmentNoteTakerBinding
    private val viewModel: HomeViewModel by viewModels { HomeFactory(requireContext()) }
    private lateinit var adapter: NoteAdapter
    private var notes = mutableListOf<Note>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteTakerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize adapter with the notes list and pass onDeleteClick and onNoteClick handlers
        adapter = NoteAdapter(notes, { note ->
            // Handle delete note
            viewModel.deleteNote(note)
            notes.remove(note)
            updateViewVisibility() // Update visibility when note is deleted
            adapter.notifyDataSetChanged()
        }, { note ->
            // Handle note click to open the TimeTrialFragment
            openTimeTrialFragment(note)
        })

        // Set up RecyclerView
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        // Load notes from ViewModel and update the adapter
        viewModel.getAllNotes { fetchedNotes ->
            notes.clear()
            notes.addAll(fetchedNotes)
            updateViewVisibility() // Update visibility when data is fetched
            adapter.notifyDataSetChanged() // Notify adapter of data changes
        }

        // Handle adding new notes
        binding.btnAdd.setOnClickListener {
            openBottomSheetAdd()
        }
    }

    // Function to open the bottom sheet for adding new notes
    private fun openBottomSheetAdd() {
        val bottomSheetFragment = AddNoteFragment { newNote ->
            notes.add(newNote)
            updateViewVisibility() // Update visibility when a new note is added
            adapter.notifyItemInserted(notes.size - 1)
        }
        bottomSheetFragment.show(childFragmentManager, "AddNoteFragment")
    }

    // Function to open the TimeTrialFragment with the selected note ID
    private fun openTimeTrialFragment(note: Note) {
        val bundle = Bundle().apply {
            putString("noteId", note.id.toString())
        }
        findNavController().navigate(R.id.timeTrialFragment, bundle)
    }

    // Function to update the visibility of the RecyclerView and ImageView
    private fun updateViewVisibility() {
        if (notes.isEmpty()) {
            binding.recycler.visibility = View.GONE
            binding.imgEmpty.visibility = View.VISIBLE
        } else {
            binding.recycler.visibility = View.VISIBLE
            binding.imgEmpty.visibility = View.GONE
        }
    }
}
