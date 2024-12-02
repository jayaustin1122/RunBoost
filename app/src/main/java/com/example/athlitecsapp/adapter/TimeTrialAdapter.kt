package com.example.athlitecsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.athlitecsapp.databinding.ItemTrialBinding
import com.example.athlitecsapp.table.TimeTrial

class TimeTrialAdapter(private val timeTrials: List<TimeTrial>) : RecyclerView.Adapter<TimeTrialAdapter.TimeTrialViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeTrialViewHolder {
        val binding = ItemTrialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeTrialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeTrialViewHolder, position: Int) {
        val timeTrial = timeTrials[position]
        holder.bind(timeTrial)
    }

    override fun getItemCount(): Int = timeTrials.size

    inner class TimeTrialViewHolder(private val binding: ItemTrialBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(timeTrial: TimeTrial) {
            binding.tvTrialNumber.text = "Trial #${timeTrial.trialNumber}"
            binding.tvTime.text = "Time: ${String.format("%.2f", timeTrial.timeInSeconds)} seconds"
            binding.tvDate.text = "Date: ${timeTrial.date}"
        }
    }
}
