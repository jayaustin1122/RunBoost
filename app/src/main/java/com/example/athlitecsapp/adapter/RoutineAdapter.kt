package com.example.athlitecsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.athlitecsapp.databinding.ItemTiniklingBinding
import com.example.athlitecsapp.model.Routine


class RoutineAdapter(
    private val tiniklingList: List<Routine>,
    private val onItemClick: (Routine, Int) -> Unit
) : RecyclerView.Adapter<RoutineAdapter.TiniklingViewHolder>() {

    inner class TiniklingViewHolder(private val binding: ItemTiniklingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tinikling: Routine) {
            binding.textViewTitle.text = tinikling.title
            binding.textViewShortDesc.text = tinikling.description

            binding.root.setOnClickListener {
                onItemClick(tinikling, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TiniklingViewHolder {
        val binding = ItemTiniklingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TiniklingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TiniklingViewHolder, position: Int) {
        val tinikling = tiniklingList[position]
        holder.bind(tinikling)
        holder.itemView.setOnClickListener {
            onItemClick(tinikling, position)
        }
    }
    override fun getItemCount(): Int = tiniklingList.size
}
