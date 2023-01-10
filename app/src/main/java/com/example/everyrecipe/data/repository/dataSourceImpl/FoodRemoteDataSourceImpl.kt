package com.example.everyrecipe.data.repository.dataSourceImpl

import android.util.Log
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.GraphQLRequest
import com.amplifyframework.api.graphql.GraphQLResponse
import com.amplifyframework.api.graphql.PaginatedResult
import com.amplifyframework.api.graphql.model.ModelPagination
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.kotlin.core.Amplify
import com.example.everyrecipe.data.model.Food
import com.example.everyrecipe.data.repository.dataSource.FoodRemoteDataSource

class FoodRemoteDataSourceImpl(): FoodRemoteDataSource {

    override suspend fun getFood(id: String): GraphQLResponse<Food>? {
        try {
            val response = Amplify.API.query(ModelQuery.get(Food::class.java, id))
            Log.i("FoodRemoteDataSourceImpl", response.data.name)
            return response
        } catch (error: ApiException) {
            Log.e("FoodRemoteDataSourceImpl", "Query failed", error)
            return null
        }
    }

    override suspend fun getAllFoods(): GraphQLResponse<PaginatedResult<Food>>? {
        return queryFirstPage()
    }

    suspend fun queryFirstPage(): GraphQLResponse<PaginatedResult<Food>>? {
        return query(
            ModelQuery.list(Food::class.java,
            ModelPagination.firstPage().withLimit(1_000)))
    }

    suspend fun query(request: GraphQLRequest<PaginatedResult<Food>>): GraphQLResponse<PaginatedResult<Food>>? {
        try {
            val response = Amplify.API.query(request)
            response.data.items.forEach { todo ->
                Log.d("FoodRemoteDataSourceImpl", todo.name)
            }
            if (response.data.hasNextResult()) {
                return query(response.data.requestForNextResult)
            }  else {
                return response
            }
        } catch (error: ApiException) {
            Log.e("FoodRemoteDataSourceImpl", "Query failed.", error)
            return null
        }
    }
}