package com.example.everyrecipe.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.everyrecipe.data.model.Ingredient
import com.example.everyrecipe.databinding.IngredientListItemBinding

class IngredientsAdapter(
    var ingredients: List<Ingredient>
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = IngredientListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    inner class ViewHolder(
        val binding: IngredientListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(ingredient: Ingredient) {
            binding.tvTitle.text = "${ingredient.IRDNT_NM} ${ingredient.IRDNT_CPCTY}"
        }
    }
}