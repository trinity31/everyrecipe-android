package com.example.everyrecipe.data.repository.dataSource

import com.amplifyframework.api.rest.RestResponse
import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.param.req.ReqParamRecommendRecipe

interface RecipeRemoteDataSource {
    suspend fun getRecommendedRecipes(params: HashMap<String, Any>): RestResponse?
}