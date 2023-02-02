package com.davinciapps.fridgemaster.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.Category
import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.util.Resource
import com.davinciapps.fridgemaster.domain.repository.FoodRepository
import com.davinciapps.fridgemaster.domain.repository.FreezerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FreezerViewModel constructor(
    app: Application,
    private val foodRepository: FoodRepository,
    private val freezerRepository: FreezerRepository
) : AndroidViewModel(app) {
    private val TAG = FreezerViewModel::class.java.simpleName

    var items: MutableLiveData<Resource<List<FreezerItem>>> = MutableLiveData()
    var categories: MutableLiveData<Resource<List<Category>>> = MutableLiveData()
    //var freezerItems: MutableLiveData<Resource<List<FreezerItem>>> = MutableLiveData()
    var existingItems: MutableLiveData<Resource<List<FreezerItem>>> = MutableLiveData()

    fun getCategories() = viewModelScope.launch(Dispatchers.IO) {
        categories.postValue(Resource.Loading())
        try {
            Log.i(TAG, "Start fetching categories.")
            val response = foodRepository.getCategories()
            Log.i(TAG, "Fetched Categories: ${response.data}")
            categories.postValue(response)
        } catch(e: Exception) {
            categories.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getItemsByCategory(categoryId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            //val response = foodRepository.getFoodsByCategory(categoryId)
            val response = freezerRepository.getItemsByCategory(categoryId)
            //Log.i(TAG, "Fetched Foods for category id ${categoryId}")
            //Log.i(TAG, "${response.data}")
            items.postValue(response)
        } catch(e: Exception) {
            items.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun setFreezerItems(items: List<FreezerItem>) = viewModelScope.launch(Dispatchers.IO) {
        Log.i(TAG, "Set freezer ${items.size} items ")
        freezerRepository.setFreezerItems(items)
    }

    fun removeFreezerItems(items: List<FreezerItem>) = viewModelScope.launch(Dispatchers.IO) {
        Log.i(TAG, "Remove freezer ${items.size} items ")
        freezerRepository.removeFreezerItems(items)
    }

//    fun getFreezerItems() = viewModelScope.launch(Dispatchers.IO) {
//        try {
//            val response = freezerRepository.getFreezerItems()
//            freezerItems.postValue(response)
//        } catch(e: Exception) {
//            freezerItems.postValue(Resource.Error(e.message.toString()))
//        }
//    }

    fun getFreezerExistingItems() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = freezerRepository.getFreezerExistingItems()
            existingItems.postValue(response)
        } catch(e: Exception) {
            existingItems.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun updateFreezerItem(freezerItem: FreezerItem, insert: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        Log.i(TAG, "Insert ${freezerItem.name} to the Fridge.")
        val result = freezerRepository.updateFreezerItem(freezerItem.copy(exist = insert))
        Log.i(TAG, "Update success? ${result}")
    }
}