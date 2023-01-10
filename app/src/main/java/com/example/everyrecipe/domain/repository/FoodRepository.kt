package com.example.everyrecipe.domain.repository

import com.example.everyrecipe.data.model.Category
import com.example.everyrecipe.data.model.Food
import com.example.everyrecipe.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun getFood(id: String): Resource<Food>
    fun getAllFoods(): Flow<List<Food>>
    suspend fun getCategoriesWithFoods(): Resource<Category>
}