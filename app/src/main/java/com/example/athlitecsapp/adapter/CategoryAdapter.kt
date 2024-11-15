package com.example.athlitecsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import android.content.Context
import com.example.athlitecsapp.databinding.CategoryItemBinding
import com.example.athlitecsapp.model.Category

class CategoryAdapter(
    private val context: Context,
    private val categories: List<Category>,
    private val itemClickListener: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.categoryTitle.text = category.title
            binding.categoryImage.setImageResource(category.imageResId)
            binding.categoryImage.setImageResource(category.imageResId)

            // Handle the click event
            itemView.setOnClickListener {
                itemClickListener(category.title)
            }
        }
    }
}
