package com.davinciapps.fridgemaster.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.davinciapps.fridgemaster.R
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.data.param.res.google.RecipeItem
import com.davinciapps.fridgemaster.databinding.RecipeListItem2Binding
import com.davinciapps.fridgemaster.presentation.viewmodel.BookmarkViewModel

class SearchResultPagingAdapter(
    var viewModel: BookmarkViewModel
): PagingDataAdapter<Recipe, SearchResultPagingAdapter.ItemViewHolder>(RecipeITEM_COMPARATOR) {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        val binding = RecipeListItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ItemViewHolder(
        val binding: RecipeListItem2Binding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            Log.i("SearchResultPagingAdapter", "${recipe.recipe}")
            binding.recipeTitle.text = recipe.recipe?.name
            binding.recipeDescription.text = recipe.recipe?.description ?: ""
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

    companion object {
        private val RecipeITEM_COMPARATOR = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldRecipeItem: Recipe, newRecipeItem: Recipe): Boolean =
                oldRecipeItem.recipe?.link == newRecipeItem.recipe?.link

            override fun areContentsTheSame(oldRecipeItem: Recipe, newRecipeItem: Recipe): Boolean =
                oldRecipeItem == newRecipeItem
        }
    }
}


