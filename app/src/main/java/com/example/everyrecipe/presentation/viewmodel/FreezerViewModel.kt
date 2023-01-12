package com.example.everyrecipe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everyrecipe.domain.repository.FoodRepository
import com.example.everyrecipe.domain.repository.FreezerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FreezerViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
//    private val freezerRepository: FreezerRepository
) : ViewModel() {
    private val TAG = FreezerViewModel::class.java.simpleName

    fun getAllFoods() = viewModelScope.launch(Dispatchers.IO) {
        foodRepository.getAllFoods()
    }
}