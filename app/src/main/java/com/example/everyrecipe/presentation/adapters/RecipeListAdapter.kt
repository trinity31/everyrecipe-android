package com.example.everyrecipe.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.everyrecipe.data.model.Recipe
import com.example.everyrecipe.databinding.RecipeListItemBinding

class RecipeListAdapter(
    var recipes: List<Recipe>
) : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>(){
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        context = parent.context
        val binding = RecipeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    inner class RecipeListViewHolder(
        val binding: RecipeListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.recipeTitle.text = recipe.recipe?.name ?: ""
            binding.recipeDescription.text = recipe.recipe?.description ?: ""
            binding.recipeIngredients.text = recipe.ingredients.joinToString(", ")

            Glide.with(binding.recipeThumbnail.context)
                .load(recipe.recipe?.imageUrl)
                .into(binding.recipeThumbnail)
        }
    }

}