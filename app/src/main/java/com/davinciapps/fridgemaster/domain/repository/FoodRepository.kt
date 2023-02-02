package com.davinciapps.fridgemaster.domain.repository

import com.amplifyframework.datastore.generated.model.Category
import com.amplifyframework.datastore.generated.model.Food
import com.davinciapps.fridgemaster.data.util.Resource

interface FoodRepository {
    suspend fun getFood(id: String): Resource<Food>
    suspend fun getAllFoods(): Resource<List<Food>>
    suspend fun getCategories(): Resource<List<Category>>
    suspend fun getFoodsByCategory(categoryId: String): Resource<List<Food>>
    suspend fun getCategoriesWithFoods(): Resource<Category>
}