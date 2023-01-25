package com.example.everyrecipe.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.Category
import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.domain.repository.FoodRepository
import com.example.everyrecipe.domain.repository.FreezerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val freezerRepository: FreezerRepository
): ViewModel() {
    private val TAG = LandingViewModel::class.java.simpleName

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private var freezerItems = mutableListOf<FreezerItem>()

    init {
        viewModelScope.launch {
           // _isLoading.value = true
            getFreezerItems()
        }
    }

    private fun getFreezerItems() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = freezerRepository.getFreezerItems()
            Log.i(TAG, "Successfully fetched freezer items. ${response.data?.size} items.")

            if(response.data?.size!! > 0) {
                Log.i(TAG, "Already has freezer items.")
                _isLoading.value = false
            } else {
                Log.i(TAG, "Start fetching foods")
                fetchAllFoods()
            }
        } catch (e: Exception) {
            Log.i(TAG, "Faild to get freezer items. ${e.message}")
        }
    }

    private fun fetchAllFoods() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = foodRepository.getAllFoods()
            Log.i(TAG, "Successfully fetched all foods.${response.data?.size} items.")
            //Insert all foods to the freezerItem DB
            response.data?.let {
                it.forEach { food ->
                    freezerItems.add(FreezerItem(
                        food.id,
                        food.category.id,
                        food.name
                    ))
                }
            }
            val result = freezerRepository.setFreezerItems(freezerItems)
            Log.i(TAG, "Set Freezer Items: $result")
            if(result) {
                _isLoading.value = false
            } else {
                Log.i(TAG, "Error setting freezer items.")
            }
        } catch (e: Exception) {
            Log.i(TAG, "Faild to get food list. ${e.message}")
          //  foods.postValue(Resource.Error(e.message.toString()))
        }
    }
}