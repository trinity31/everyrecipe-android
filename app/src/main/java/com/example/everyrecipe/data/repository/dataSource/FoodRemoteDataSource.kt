package com.example.everyrecipe.data.repository.dataSource

import com.amplifyframework.api.graphql.GraphQLResponse
import com.amplifyframework.api.graphql.PaginatedResult
import com.amplifyframework.datastore.generated.model.Food

interface FoodRemoteDataSource {
    suspend fun getFood(id: String): GraphQLResponse<Food>?
    suspend fun getAllFoods(): GraphQLResponse<PaginatedResult<Food>>?
}