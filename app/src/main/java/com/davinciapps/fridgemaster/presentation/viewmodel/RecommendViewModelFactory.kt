package com.davinciapps.fridgemaster.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.davinciapps.fridgemaster.domain.repository.RecipeRepository

class RecommendViewModelFactory(
    private val app: Application,
    private val recipeRepository: RecipeRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecommendViewModel(app, recipeRepository) as T
    }
}