package com.davinciapps.fridgemaster.data.repository.dataSource

import androidx.paging.PagingData
import com.amplifyframework.api.rest.RestResponse
import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.data.param.res.google.GoogleSearchResponse
import com.davinciapps.fridgemaster.data.param.res.google.RecipeItem
import com.davinciapps.fridgemaster.data.param.res.openapi.IngResponse
import com.davinciapps.fridgemaster.data.param.res.openapi.ProcResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RecipeRemoteDataSource {
    suspend fun getRecommendedRecipes(params: HashMap<String, Any>): RestResponse?
    suspend fun getSearchedRecipes(params: HashMap<String, Any>): RestResponse?
    suspend fun getRecipeIngredients(recipeId: String): Response<IngResponse>
    suspend fun getRecipeProcedures(recipeId: String): Response<ProcResponse>
    suspend fun getRecommendWebRecipes(items: List<FreezerItem>): Response<GoogleSearchResponse>

    fun getRecommendedWebRecipesStream(items: List<FreezerItem>): Flow<PagingData<Recipe>>
}