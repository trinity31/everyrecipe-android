package com.example.everyrecipe.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.everyrecipe.domain.repository.RecipeRepository

class BookmarkViewModelFactory(
    private val app: Application,
    private val recipeRepository: RecipeRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookmarkViewModel(app, recipeRepository) as T
    }
}