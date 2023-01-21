package com.example.everyrecipe.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Food
import com.bumptech.glide.Glide
import com.example.everyrecipe.R
import com.example.everyrecipe.data.model.Recipe
import com.example.everyrecipe.databinding.RecipeListItemBinding
import com.example.everyrecipe.presentation.viewmodel.BookmarkViewModel

class RecipeListAdapter(
    var recipes: List<Recipe>,
    var viewModel: BookmarkViewModel
) : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>(){
    private val TAG = RecipeListAdapter::class.java.simpleName

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        context = parent.context
        val binding = RecipeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
        holder.binding.bookmarkIcon.setOnClickListener {
            Log.i(TAG, "Recipe clicked: $recipe")
            if(viewModel.isBookmarked(recipe)) {
                viewModel.removeFromBookmark(recipe)
            } else {
                viewModel.addToBookmark(recipe)
            }
        }
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

            val image = if(viewModel.isBookmarked(recipe))
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_bookmark_24)
            else ContextCompat.getDrawable(context, R.drawable.ic_baseline_bookmark_border_24)

            binding.bookmarkIcon.setImageDrawable(image)
        }
    }

}