package com.example.athlitecsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.athlitecsapp.R
import com.example.athlitecsapp.databinding.ItemTiniklingBinding
import com.example.athlitecsapp.ui.home.onehundred.Take100Fragment.*
import com.example.athlitecsapp.util.VideoItem
class VideoItemAdapter(
    private val videoList: List<VideoItem>,
    private val onItemClicked: (Int) -> Unit, // Lambda to handle item click
    private var activePosition: Int // Tracks the active item
) : RecyclerView.Adapter<VideoItemAdapter.VideoItemViewHolder>() {

    inner class VideoItemViewHolder(val binding: ItemTiniklingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoItem, position: Int) {
            binding.textViewTitle.text = item.title
            binding.textViewShortDesc.text = item.description
            binding.shapeableImageView.setImageResource(item.cover)

            // Click listener for each item
            binding.root.setOnClickListener {
                if (position != activePosition) {
                    // Pass the clicked item's position to the fragment/activity via the lambda
                    onItemClicked(position)

                    // Update the active position and refresh the UI
                    updateActivePosition(position)
                }
            }

            // Update the UI to reflect whether this item is the active one
            if (position == activePosition) {
                // This item is currently active
                binding.card.setBackgroundColor(
                    ContextCompat.getColor(binding.root.context, R.color.btn_bg)
                )
            } else {
                // This item is not active (default/inactive state)
                binding.card.setBackgroundColor(
                    ContextCompat.getColor(binding.root.context, R.color.white)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {
        val binding = ItemTiniklingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoItemViewHolder, position: Int) {
        holder.bind(videoList[position], position)
    }

    override fun getItemCount(): Int = videoList.size

    // Update the active position and refresh the entire list
    fun updateActivePosition(newPosition: Int) {
        // If the clicked item is not the same as the current active one, update it
        if (activePosition != newPosition) {
            val previousPosition = activePosition
            activePosition = newPosition

            // Notify only the two affected items to optimize performance
            notifyItemChanged(previousPosition) // Refresh the old active item
            notifyItemChanged(newPosition)      // Refresh the new active item
        }
    }
}
