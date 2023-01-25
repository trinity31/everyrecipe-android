package com.example.everyrecipe.presentation.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Food
import com.example.everyrecipe.R
import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.databinding.AdapterFreezerCategoryItemBinding
import com.example.everyrecipe.presentation.viewmodel.FreezerViewModel
import com.google.android.material.chip.Chip

class FreezerCategoryAdapter(
    var categoryName: String,
    var items: List<FreezerItem>,
    var viewModel: FreezerViewModel
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

    inner class CategoryViewHolder(
        val binding: AdapterFreezerCategoryItemBinding
        ): RecyclerView.ViewHolder(binding.root){
        fun bind() {
            binding.tvTitle.text = categoryName
            binding.chipGroup.removeAllViews()
            items.forEach { item ->
                val chip = Chip(context)
                chip.text = item.name

                binding.chipGroup.addView(chip)
                chip.isCheckable = true
                chip.isChecked = checkFoodExistsInFreezer(item.name)

                if(chip.isChecked) {
                    chip.setTextColor(ContextCompat.getColor(context, R.color.surface))
                    chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.blood_orange)))
                    chip.textSize = 20.0f
                } else {
                    chip.setTextColor(ContextCompat.getColor(context, R.color.light_gray))
                    chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.light_green)))
                    chip.textSize = 18.0f
                }

//                chip.setOnClickListener {
//                    val index = binding.chipGroup.indexOfChild(it)
//                    Log.i("FreezerCategoryAdapter", "${items[index].name} clicked, checked: ${chip.isChecked}")
//                }
                chip.setOnCheckedChangeListener { buttonView, isChecked ->
                    Log.i("FreezerCategoryAdapter", "isChecked: $isChecked, index: ${binding.chipGroup.indexOfChild(chip)}")
                    val freezerItem = items[binding.chipGroup.indexOfChild(chip)]
                   // val freezerItem = FreezerItem(food.id, food.category.id, food.name)
                    Log.i("FreezerCategoryAdapter", "Add New Freezer Item: $freezerItem")
                    if(isChecked) {
                        //viewModel.setFreezerItems(listOf(freezerItem))
                        viewModel.updateFreezerItem(freezerItem, true) //add to the fridge
                        chip.setTextColor(ContextCompat.getColor(context, R.color.surface))
                        chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.blood_orange)))
                    } else {
                       // viewModel.removeFreezerItems(listOf(freezerItem))
                        viewModel.updateFreezerItem(freezerItem, false) //remove from the fridge
                        chip.setTextColor(ContextCompat.getColor(context, R.color.light_gray))
                        chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.light_green)))
                    }
                }
            }
        }
    }

    private fun checkFoodExistsInFreezer(name: String): Boolean {
        val item = items.find { it.name == name }
        return item?.exist ?: false
    }
}