package com.example.athlitecsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.athlitecsapp.databinding.ItemViewsListBinding
import com.example.athlitecsapp.model.Routine

class RoutineAdapter(var list: MutableList<Routine>) : RecyclerView.Adapter<RoutineAdapter.ViewHolderAdapter>() {
    private var expandedPosition: Int = RecyclerView.NO_POSITION

    inner class ViewHolderAdapter(val binding: ItemViewsListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemViewsListBinding.inflate(inflater, parent, false)
        return ViewHolderAdapter(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderAdapter, position: Int) {
        val currentPosition = holder.adapterPosition
        val data = list[currentPosition]
        val isExpandable = currentPosition == expandedPosition

        holder.binding.apply {
            titleText.text = data.title
            workoutImage.visibility = if (isExpandable) View.VISIBLE else View.GONE

            Glide.with(workoutImage.context)
                .load(data.videoId)
                .into(workoutImage)

            root.setOnClickListener {
                val previousExpandedPosition = expandedPosition

                // Collapse the previously expanded item
                if (previousExpandedPosition != RecyclerView.NO_POSITION) {
                    list[previousExpandedPosition].isExpandable = false
                    notifyItemChanged(previousExpandedPosition)
                }

                // Expand or collapse the clicked item
                if (previousExpandedPosition == currentPosition) {
                    expandedPosition = RecyclerView.NO_POSITION
                } else {
                    expandedPosition = currentPosition
                    list[currentPosition].isExpandable = true
                }

                notifyItemChanged(currentPosition)
            }
        }
    }


    override fun getItemCount(): Int = list.size

    fun onApplySearch(filteredList: ArrayList<Routine>) {
        this.list = filteredList
        notifyDataSetChanged()
    }
}