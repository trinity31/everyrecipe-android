package com.example.everyrecipe.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.Food
import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.domain.repository.FoodRepository
import com.example.everyrecipe.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val recipeRepository: RecipeRepository
): ViewModel() {
    private val TAG = SearchViewModel::class.java.simpleName

    //전체 음식 목록
    var foods: MutableLiveData<Resource<List<Food>>> = MutableLiveData()
    //검색 또는 냉장고에서 선택해서 레시피 검색에 사용할 음식 목록(레시피 검색에 사용)
    var searchFoods: MutableList<FreezerItem> = mutableListOf()
    //자동완성 리스트에 나타나는 음식 목록
    var filteredFoods: MutableLiveData<List<Food>> = MutableLiveData()

    fun getAllFoods() = viewModelScope.launch(Dispatchers.IO) {
        foods.postValue(Resource.Loading())
        try {
            val response = foodRepository.getAllFoods()
            foods.postValue(response)
        } catch (e: Exception) {
            Log.i(TAG, "Faild to get food list. ${e.message}")
            foods.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun searchFood(text: String) {
        if(foods.value is Resource.Success) {
            val items = (foods.value as Resource.Success<List<Food>>).data as List<Food>
            filteredFoods.value = items.filter {
                it.name.contains(text)
            }
        }
    }

    fun addToSearchFoods(item: FreezerItem) {
        Log.i(TAG, "addToSearchFoods, item: $item")
        searchFoods.add(item)
        filteredFoods.value = listOf()
    }

    fun removeFromSearchFoods(name: String?): Int {
        Log.i(TAG, "Remove $name")
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

    fun searchRecipes() {
        Log.i(TAG, "Search Recipes with..")
        searchFoods.forEach {
            Log.i(TAG, it.name)
        }
    }
}