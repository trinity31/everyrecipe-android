package com.example.everyrecipe.data.repository

import com.amplifyframework.api.graphql.GraphQLResponse
import com.example.everyrecipe.data.model.Category
import com.example.everyrecipe.data.model.Food
import com.example.everyrecipe.data.repository.dataSource.FoodRemoteDataSource
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow

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

    override fun getAllFoods(): Flow<List<Food>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoriesWithFoods(): Resource<Category> {
        TODO("Not yet implemented")
    }
}