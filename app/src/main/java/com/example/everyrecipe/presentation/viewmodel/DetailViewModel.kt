package com.example.everyrecipe.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everyrecipe.data.model.Ingredient
import com.example.everyrecipe.data.model.Procedure
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {
    private val TAG = DetailViewModel::class.java.simpleName

    var ingredients: MutableLiveData<Resource<List<Ingredient>>> = MutableLiveData()
    var procedures: MutableLiveData<Resource<List<Procedure>>> = MutableLiveData()

    fun getIngredients(recipeId: String) = viewModelScope.launch(Dispatchers.IO) {
        ingredients.postValue(Resource.Loading())
        try {
            val response = recipeRepository.getRecipeIngredients(recipeId)
            ingredients.postValue(response)
        } catch(e: Exception) {
            ingredients.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getProcedures(recipeId: String) = viewModelScope.launch(Dispatchers.IO) {
        procedures.postValue(Resource.Loading())
        try {
            val response = recipeRepository.getRecipeProcedures(recipeId)
            procedures.postValue(response)
        } catch(e: Exception) {
            procedures.postValue(Resource.Error(e.message.toString()))
        }
    }
}