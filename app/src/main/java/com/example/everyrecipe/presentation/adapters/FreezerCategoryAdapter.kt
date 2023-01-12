package com.example.everyrecipe.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Food
import com.example.everyrecipe.databinding.AdapterFreezerCategoryItemBinding
import com.google.android.material.chip.Chip

class FreezerCategoryAdapter(
    var categoryName: String,
    var foods: List<Food>
) : RecyclerView.Adapter<FreezerCategoryAdapter.CategoryViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        context = parent.context
        val binding = AdapterFreezerCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class CategoryViewHolder(val binding: AdapterFreezerCategoryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind() {
            binding.tvTitle.text = categoryName
            binding.chipGroup.removeAllViews()
            foods.forEach { food ->
                val chip = Chip(context)
                chip.text = food.name
                binding.chipGroup.addView(chip)
                chip.isCheckable = true
                chip.setOnClickListener {
                    val index = binding.chipGroup.indexOfChild(it)
                    Log.i("FreezerCategoryAdapter", "${foods[index].name} clicked, checked: ${chip.isChecked}")
                }
                chip.setOnCheckedChangeListener { buttonView, isChecked ->
                    Log.i("FreezerCategoryAdapter", "isChecked: $isChecked")
                }
            }
        }
    }
}