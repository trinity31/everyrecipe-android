package com.davinciapps.fridgemaster.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.data.param.res.google.RecipeItem
import com.davinciapps.fridgemaster.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GooglePagingViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {
    private val TAG = GooglePagingViewModel::class.java.simpleName

    fun search(items: List<FreezerItem>): Flow<PagingData<Recipe>> {
        return recipeRepository.getRecommendedWebRecipesStream(items)
    }
}