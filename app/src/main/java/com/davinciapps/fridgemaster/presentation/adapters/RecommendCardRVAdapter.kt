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
import com.davinciapps.fridgemaster.presentation.home.DetailWebActivity
import com.davinciapps.fridgemaster.presentation.viewmodel.BookmarkViewModel

class RecommendCardRVAdapter(
    var recipes: List<Recipe>,
    var viewModel: BookmarkViewModel,
    var showIng: Boolean
) : RecyclerView.Adapter<RecommendCardRVAdapter.RecipeListRVViewHolder>(){
    private val TAG = RecipeListAdapter::class.java.simpleName

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListRVViewHolder {
        context = parent.context
        val binding = RecipeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeListRVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeListRVViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe, showIng)
        holder.binding.bookmarkIcon.setOnClickListener {
            if(viewModel.isBookmarked(recipe)) {
                viewModel.removeFromBookmark(recipe)
            } else {
                viewModel.addToBookmark(recipe)
            }
        }
        holder.binding.container.setOnClickListener {
            Log.i(TAG, "Recipe clicked: $recipe")
            if(recipe.recipe?.link != "") {
                val intent = Intent(context, DetailWebActivity::class.java)
                intent.putExtra("url", recipe.recipe?.link)
                context.startActivity(intent)
            } else {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("title", recipe.recipe?.name)
                intent.putExtra("id", recipe.recipe?.id)
                intent.putExtra("imageUrl", recipe.recipe?.imageUrl)
                intent.putExtra("description", recipe.recipe?.description)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    inner class RecipeListRVViewHolder(
        val binding: RecipeListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe, showIng: Boolean) {
            binding.recipeTitle.text = recipe.recipe?.name ?: ""
            binding.recipeDescription.text = recipe.recipe?.description ?: ""

            binding.recipeIngredients.text = if(showIng) recipe.ingredients.joinToString(", ") else ""

            val imageUrl = if(recipe.recipe?.thumbnailImage.isNullOrEmpty()) recipe.recipe?.imageUrl
            else recipe.recipe?.thumbnailImage

            Glide.with(binding.recipeThumbnail.context)
                .load(imageUrl)
                .into(binding.recipeThumbnail)

            val image = if(viewModel.isBookmarked(recipe))
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_bookmark_24)
            else ContextCompat.getDrawable(context, R.drawable.ic_baseline_bookmark_border_24)

            binding.bookmarkIcon.setImageDrawable(image)
        }
    }

}