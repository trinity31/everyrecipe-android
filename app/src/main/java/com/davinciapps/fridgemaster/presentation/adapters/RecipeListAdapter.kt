package com.davinciapps.fridgemaster.presentation.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.davinciapps.fridgemaster.R
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.databinding.RecipeListItemBinding
import com.davinciapps.fridgemaster.presentation.home.DetailActivity
import com.davinciapps.fridgemaster.presentation.viewmodel.BookmarkViewModel

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
            if(viewModel.isBookmarked(recipe)) {
                viewModel.removeFromBookmark(recipe)
            } else {
                viewModel.addToBookmark(recipe)
            }
        }
        holder.binding.container.setOnClickListener {
            Log.i(TAG, "Recipe clicked: $recipe")
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("title", recipe.recipe?.name)
            intent.putExtra("id", recipe.recipe?.id)
            intent.putExtra("imageUrl", recipe.recipe?.imageUrl)
            intent.putExtra("description", recipe.recipe?.description)
            context.startActivity(intent)
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