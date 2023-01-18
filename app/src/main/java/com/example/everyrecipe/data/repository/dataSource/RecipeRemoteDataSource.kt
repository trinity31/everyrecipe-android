package com.example.everyrecipe.data.repository.dataSource

import com.amplifyframework.api.rest.RestResponse

interface RecipeRemoteDataSource {
    suspend fun getRecommendedRecipes(params: HashMap<String, Any>): RestResponse?
    suspend fun getSearchedRecipes(params: HashMap<String, Any>): RestResponse?
}