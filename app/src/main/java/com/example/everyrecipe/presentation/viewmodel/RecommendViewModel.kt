package com.example.everyrecipe.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.model.Recipe
import com.example.everyrecipe.data.param.req.ReqParamSearchRecipe
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel(){
    private val TAG = RecommendViewModel::class.java.simpleName

    var recipes: MutableLiveData<Resource<List<Recipe>>> = MutableLiveData()

    fun getRecommendedRecipes(freezerItems: List<FreezerItem>) = viewModelScope.launch(Dispatchers.IO) {
        val param = ReqParamSearchRecipe(searchItems = freezerItems)
        recipes.postValue(Resource.Loading())
        try {
            val response = recipeRepository.getRecommendedRecipes(param)
            recipes.postValue(response)
        } catch (e: Exception) {
            recipes.postValue(Resource.Error(e.message.toString()))
        }
    }
}