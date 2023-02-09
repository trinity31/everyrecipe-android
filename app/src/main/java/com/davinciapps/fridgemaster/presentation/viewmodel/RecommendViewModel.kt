package com.davinciapps.fridgemaster.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.data.param.req.ReqParamSearchRecipe
import com.davinciapps.fridgemaster.data.util.Resource
import com.davinciapps.fridgemaster.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecommendViewModel constructor(
    private val app: Application,
    private val recipeRepository: RecipeRepository
): AndroidViewModel(app){
    private val TAG = RecommendViewModel::class.java.simpleName

    var recipes: MutableLiveData<Resource<List<Recipe>>> = MutableLiveData()
    var recipesWeb: MutableLiveData<Resource<List<Recipe>>> = MutableLiveData()

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

    fun getRecommendedWebRecipes(freezerItems: List<FreezerItem>) = viewModelScope.launch(Dispatchers.IO) {
        recipesWeb.postValue(Resource.Loading())
        //Log.i(TAG, "freezerItems: $freezerItems")
        try {
            val response = recipeRepository.getRecommendedWebRecipes(freezerItems)
            //Log.i(TAG, "response: ${response.data}")
            recipesWeb.postValue(response)
        } catch (e: Exception) {
            recipesWeb.postValue(Resource.Error(e.message.toString()))
        }
    }
}