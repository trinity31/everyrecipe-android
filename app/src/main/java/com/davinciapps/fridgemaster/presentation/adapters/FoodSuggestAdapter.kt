package com.davinciapps.fridgemaster.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.databinding.FoodSuggestListItemBinding

class FoodSuggestAdapter(
    var foods: List<FreezerItem>
): RecyclerView.Adapter<FoodSuggestAdapter.FoodSuggestViewHolder>() {
    private lateinit var context: Context
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(food: FreezerItem)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodSuggestViewHolder {
        context = parent.context
        val binding = FoodSuggestListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodSuggestViewHolder(binding)
    }


    override fun onBindViewHolder(holder: FoodSuggestViewHolder, position: Int) {
        holder.bind(foods[position])
        holder.binding.tvTitle.setOnClickListener {
            listener?.onItemClick(foods[position])
        }
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    inner class FoodSuggestViewHolder(
        val binding: FoodSuggestListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(food: FreezerItem) {
            binding.tvTitle.text = food.name
        }
    }

}