package com.example.everyrecipe.presentation.viewmodel

import android.app.Application
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

    fun addToBookmark(item: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        recipeRepository.saveRecipeToBookmark(item)
    }

    fun removeFromBookmark(item: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        recipeRepository.deleteRecipeFromBookmark(item)
    }

    fun getBookmarkedItems() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = recipeRepository.getBookmarkedRecipes()
            bookmarks.postValue(response)
        } catch (e: Exception) {
            bookmarks.postValue(Resource.Error(e.message.toString()))
        }
    }
}