package com.example.everyrecipe.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.Food
import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.model.Recipe
import com.example.everyrecipe.data.param.req.ReqParamSearchRecipe
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.domain.repository.FoodRepository
import com.example.everyrecipe.domain.repository.FreezerRepository
import com.example.everyrecipe.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel constructor(
    app: Application,
    private val foodRepository: FoodRepository,
    private val recipeRepository: RecipeRepository,
    private val freezerRepository: FreezerRepository
): AndroidViewModel(app) {
    private val TAG = SearchViewModel::class.java.simpleName

    //전체 음식 목록
    var foods: MutableLiveData<Resource<List<FreezerItem>>> = MutableLiveData()
    //검색 또는 냉장고에서 선택해서 레시피 검색에 사용할 음식 목록(레시피 검색에 사용)
    var searchFoods: MutableList<FreezerItem> = mutableListOf()
    //자동완성 리스트에 나타나는 음식 목록
    var filteredFoods: MutableLiveData<List<FreezerItem>> = MutableLiveData()
    //레시피 검색 결과
    var recipes: MutableLiveData<Resource<List<Recipe>>> = MutableLiveData()

    fun getAllFoods() = viewModelScope.launch(Dispatchers.IO) {
        foods.postValue(Resource.Loading())
        try {
            val response = freezerRepository.getFreezerItems()
            Log.i(TAG, "Successfully fetched all foods.")
            foods.postValue(response)
        } catch (e: Exception) {
            Log.i(TAG, "Faild to get food list. ${e.message}")
            foods.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun searchFood(text: String) {
        if(foods.value is Resource.Success) {
            val items = (foods.value as Resource.Success<List<FreezerItem>>).data as List<FreezerItem>
            filteredFoods.value = items.filter {
                it.name.contains(text)
            }
        }
    }

    fun addToSearchFoods(item: FreezerItem) {
        searchFoods.add(item)
        filteredFoods.value = listOf()
    }

    fun removeFromSearchFoods(name: String?): Int {
        var indexToRemove = -1
        searchFoods.filterIndexed { index, freezerItem ->
            if(freezerItem.name == name) {
                indexToRemove = index
                false
            } else true
        }
        if(indexToRemove != -1) {
            searchFoods.removeAt(indexToRemove)
        }
        return indexToRemove
    }

    fun cancelSearchFoods() {
        filteredFoods.value = listOf()
    }

    fun searchRecipes() = viewModelScope.launch(Dispatchers.IO) {
        val param = ReqParamSearchRecipe(searchItems = searchFoods)
        recipes.postValue(Resource.Loading())
        try {
            val response = recipeRepository.getSearchedRecipes(param)
            recipes.postValue(response)
        } catch (e: Exception) {
            recipes.postValue(Resource.Error(e.message.toString()))
        }
    }
}