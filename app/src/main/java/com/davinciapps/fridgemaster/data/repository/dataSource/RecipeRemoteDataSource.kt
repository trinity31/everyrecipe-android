package com.davinciapps.fridgemaster.data.repository.dataSource

import GoogleSearchResponse
import com.amplifyframework.api.rest.RestResponse
import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.param.res.openapi.IngResponse
import com.davinciapps.fridgemaster.data.param.res.openapi.ProcResponse
import retrofit2.Response

interface RecipeRemoteDataSource {
    suspend fun getRecommendedRecipes(params: HashMap<String, Any>): RestResponse?
    suspend fun getSearchedRecipes(params: HashMap<String, Any>): RestResponse?
    suspend fun getRecipeIngredients(recipeId: String): Response<IngResponse>
    suspend fun getRecipeProcedures(recipeId: String): Response<ProcResponse>
    suspend fun getRecommendWebRecipes(items: List<FreezerItem>): Response<GoogleSearchResponse>
}