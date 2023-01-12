package com.example.everyrecipe.data.repository.dataSourceImpl

import android.util.Log
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.GraphQLRequest
import com.amplifyframework.api.graphql.GraphQLResponse
import com.amplifyframework.api.graphql.PaginatedResult
import com.amplifyframework.api.graphql.model.ModelPagination
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.datastore.generated.model.Category
import com.amplifyframework.datastore.generated.model.Food
import com.amplifyframework.kotlin.core.Amplify
import com.example.everyrecipe.data.repository.dataSource.FoodRemoteDataSource

class FoodRemoteDataSourceImpl(): FoodRemoteDataSource {
    private val TAG = FoodRemoteDataSourceImpl::class.java.simpleName
    
    override suspend fun getFood(id: String): GraphQLResponse<Food>? {
        try {
            val response = Amplify.API.query(ModelQuery.get(Food::class.java, id))
            Log.i(TAG, response.data.name)
            return response
        } catch (error: ApiException) {
            Log.e(TAG, "Query failed", error)
            return null
        }
    }

    override suspend fun getAllFoods(): GraphQLResponse<PaginatedResult<Food>>? {
        return queryFirstPage()
    }

    override suspend fun getCategories(): GraphQLResponse<PaginatedResult<Category>>? {
        try {
            val categoryList = Amplify.API
                .query(ModelQuery.list(Category::class.java, Category.ID.ne("")))
            categoryList.data.items.forEach { category -> Log.i(TAG, category.name) }
            return categoryList
        } catch (error: ApiException) {
            Log.e(TAG, "Query failure", error)
            return null
        }
    }

    override suspend fun getFoodsByCategory(categoryId: String): GraphQLResponse<PaginatedResult<Food>>? {
        try {
            val foodList = Amplify.API
                .query(ModelQuery.list(Food::class.java, Food.CATEGORY.contains(categoryId)))
            foodList.data.items.forEach { food -> Log.i(TAG, food.name) }
            return foodList
        } catch (error: ApiException) {
            Log.e(TAG, "Query failure", error)
            return null
        }
    }

    suspend fun queryFirstPage(): GraphQLResponse<PaginatedResult<Food>>? {
        return query(
            ModelQuery.list(Food::class.java,
            ModelPagination.firstPage().withLimit(1_000)))
    }

    suspend fun query(request: GraphQLRequest<PaginatedResult<Food>>): GraphQLResponse<PaginatedResult<Food>>? {
        try {
            val response = Amplify.API.query(request)
            if (response.data.hasNextResult()) {
                Log.d(TAG, "Get next items")
                    return query(response.data.requestForNextResult)
            }  else {
//                response.data.items.forEach {
//                    Log.i(TAG, "food: $it")
//                }
                Log.d(TAG, "No next items. Total ${response.data.items.count()} items")
                return response
            }
        } catch (error: ApiException) {
            Log.e(TAG, "Query failed.", error)
            return null
        }
    }
}