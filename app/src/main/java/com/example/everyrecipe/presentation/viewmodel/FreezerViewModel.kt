package com.example.everyrecipe.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.Category
import com.amplifyframework.datastore.generated.model.Food
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.domain.repository.FoodRepository
import com.example.everyrecipe.domain.repository.FreezerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FreezerViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
//    private val freezerRepository: FreezerRepository
) : ViewModel() {
    private val TAG = FreezerViewModel::class.java.simpleName
    var foods: MutableLiveData<Resource<List<Food>>> = MutableLiveData()
    var categories: MutableLiveData<Resource<List<Category>>> = MutableLiveData()

    fun getAllFoods() = viewModelScope.launch(Dispatchers.IO) {
        //foods.postValue(Resource.Loading())
        val result = foodRepository.getAllFoods()
        //foods.postValue(result)
    }

    fun getCategories() = viewModelScope.launch(Dispatchers.IO) {
        categories.postValue(Resource.Loading())
        try {
            val response = foodRepository.getCategories()
            Log.i(TAG, "Fetched Categories: ${response.data}")
            categories.postValue(response)
        } catch(e: Exception) {
            categories.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getFoodsByCategory(categoryId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = foodRepository.getFoodsByCategory(categoryId)
            //Log.i(TAG, "Fetched Foods for category id ${categoryId}")
            //Log.i(TAG, "${response.data}")
            foods.postValue(response)
        } catch(e: Exception) {
            foods.postValue(Resource.Error(e.message.toString()))
        }
    }
}