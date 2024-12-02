package com.example.athlitecsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.athlitecsapp.databinding.DanceExampleItemBinding
import com.example.athlitecsapp.model.Routine

class TiniklingVideoAdapter(
    private val tiniklingList: List<Routine>,
    private val context: Context,
    private val onItemClick: (Routine) -> Unit // Pass a single Tinikling item
) : RecyclerView.Adapter<TiniklingVideoAdapter.TiniklingViewHolder>() {

    inner class TiniklingViewHolder(private val binding: DanceExampleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tinikling: Routine) {
            binding.danceTitle.text = tinikling.title
            val coverResourceId = tinikling.image
            if (coverResourceId != null) {
                Glide.with(context)
                    .load(coverResourceId)
                    .into(binding.danceImage)
            }
            // Set the image if you have one. Uncomment the line below if you have a valid image source.
            // tinikling.video?.let { binding.danceImage.setImageResource(it) }

            // Handle item click event
            binding.root.setOnClickListener {
                onItemClick(tinikling) // Pass the current tinikling item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TiniklingViewHolder {
        val binding = DanceExampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TiniklingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TiniklingViewHolder, position: Int) {
        holder.bind(tiniklingList[position])
    }

    override fun getItemCount(): Int = tiniklingList.size
}
