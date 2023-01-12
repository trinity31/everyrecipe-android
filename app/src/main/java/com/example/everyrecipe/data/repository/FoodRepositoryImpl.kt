package com.example.everyrecipe.data.repository

import android.util.Log
import com.amplifyframework.api.graphql.GraphQLResponse
import com.amplifyframework.api.graphql.PaginatedResult
import com.amplifyframework.datastore.generated.model.Food
import com.example.everyrecipe.data.model.Category
import com.example.everyrecipe.data.repository.dataSource.FoodRemoteDataSource
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.domain.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FoodRepositoryImpl(
    private val foodRemoteDataSource: FoodRemoteDataSource
): FoodRepository {
    override suspend fun getFood(id: String): Resource<Food> {
        return responceToResource(foodRemoteDataSource.getFood(id))
    }

    private fun responceToResource(response: GraphQLResponse<Food>?): Resource<Food> {
        return if(response?.hasData() == true) {
            Resource.Success(response.data)
        } else {
            Resource.Error(response.toString())
        }
    }

    override suspend fun getAllFoods(): Flow<List<Food>>? {
        return foodRemoteDataSource.getAllFoods()?.toFlow()
    }

    private fun GraphQLResponse<PaginatedResult<Food>>.toFlow(): Flow<List<Food>> = flow {
        val items = mutableListOf<Food>()
        for (page in this@toFlow.data) {
            items.add(page)
        }
        emit(items.toList())
    }


    override suspend fun getCategoriesWithFoods(): Resource<Category> {
        TODO("Not yet implemented")
    }
}