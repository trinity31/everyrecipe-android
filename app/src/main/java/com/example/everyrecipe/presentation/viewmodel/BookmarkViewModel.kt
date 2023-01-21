package com.example.everyrecipe.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.everyrecipe.data.model.Recipe
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarkViewModel constructor(
    app: Application,
    private val recipeRepository: RecipeRepository
) : AndroidViewModel(app) {
    private val TAG = BookmarkViewModel::class.java.simpleName

    var bookmarks: MutableLiveData<Resource<List<Recipe>>> = MutableLiveData()

    private var bookmark_in_op = false
    var bookmark_result: MutableLiveData<Long?> = MutableLiveData()

    fun addToBookmark(item: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        if(bookmark_in_op) {
            Log.i(TAG, "Bookmark is in operation.")
        } else {
            bookmark_in_op = true
            val result = recipeRepository.saveRecipeToBookmark(item)
            bookmark_result.postValue(result)
            Log.i(TAG, "Bookmark is added.")
            bookmark_in_op = false
        }
    }

    fun removeFromBookmark(item: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        if(bookmark_in_op) {
            Log.i(TAG, "Bookmark is in operation.")
        } else {
            bookmark_in_op = true
            bookmarks.value?.data?.forEach {
                if(it.recipe?.id == item.recipe?.id) {
                    val result = recipeRepository.deleteRecipeFromBookmark(it)
                    bookmark_result.postValue(result.toLong())
                }
            }
            Log.i(TAG, "Bookmark is removed.")
            bookmark_in_op = false
        }
    }

    fun getBookmarkedItems() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = recipeRepository.getBookmarkedRecipes()
            bookmarks.postValue(response)
        } catch (e: Exception) {
            bookmarks.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun isBookmarked(recipe: Recipe): Boolean {
       // Log.i(TAG, "recipe: $recipe")

        bookmarks.value?.data?.forEach {
          //  Log.i(TAG, it.toString())
            if(it.recipe?.id == recipe.recipe?.id) {
                Log.i(TAG, "${recipe.recipe?.name} is bookmarked")
                return true
            }
        }
        return false
    }
}