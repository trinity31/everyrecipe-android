package com.example.everyrecipe.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.everyrecipe.domain.repository.FoodRepository
import com.example.everyrecipe.domain.repository.FreezerRepository

class FreezerViewModelFactory(
    private val app: Application,
    private val foodRepository: FoodRepository,
    private val freezerRepository: FreezerRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FreezerViewModel(app, foodRepository, freezerRepository) as T
    }
}