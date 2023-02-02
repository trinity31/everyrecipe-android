package com.davinciapps.fridgemaster.data.repository.dataSource

import com.amplifyframework.api.graphql.GraphQLResponse
import com.amplifyframework.api.graphql.PaginatedResult
import com.amplifyframework.datastore.generated.model.Category
import com.amplifyframework.datastore.generated.model.Food

interface FoodRemoteDataSource {
    suspend fun getFood(id: String): GraphQLResponse<Food>?
    suspend fun getAllFoods(): GraphQLResponse<PaginatedResult<Food>>?
    suspend fun getCategories(): GraphQLResponse<PaginatedResult<Category>>?
    suspend fun getFoodsByCategory(categoryId: String): GraphQLResponse<PaginatedResult<Food>>?
}