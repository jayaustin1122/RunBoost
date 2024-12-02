package com.example.athlitecsapp.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.athlitecsapp.databinding.ItemNoteBinding
import com.example.athlitecsapp.table.Note
class NoteAdapter(
    private var notes: MutableList<Note> ,
    private val onDeleteClick: (Note) -> Unit,
    private val onNoteClick: (Note) -> Unit // Use MutableList here as well
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.tvNoteTitle.text = note.title ?: "Untitled"

            binding.btnDelete.setOnClickListener {
                onDeleteClick(note)
            }
            binding.root.setOnClickListener {
                onNoteClick(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size
}
