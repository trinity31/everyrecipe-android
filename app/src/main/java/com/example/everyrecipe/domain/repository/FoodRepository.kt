package com.example.everyrecipe.domain.repository

import com.amplifyframework.datastore.generated.model.Category
import com.amplifyframework.datastore.generated.model.Food
import com.example.everyrecipe.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun getFood(id: String): Resource<Food>
    suspend fun getAllFoods(): Flow<List<Food>>?
    suspend fun getCategories(): Resource<List<Category>>
    suspend fun getFoodsByCategory(categoryId: String): Resource<List<Food>>
    suspend fun getCategoriesWithFoods(): Resource<Category>
}