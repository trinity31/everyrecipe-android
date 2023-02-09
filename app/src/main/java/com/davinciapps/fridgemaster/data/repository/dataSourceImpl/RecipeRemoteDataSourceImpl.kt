package com.davinciapps.fridgemaster.data.repository.dataSourceImpl

import GoogleSearchResponse
import android.util.Log
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.rest.RestOptions
import com.amplifyframework.api.rest.RestResponse
import com.amplifyframework.kotlin.core.Amplify
import com.davinciapps.fridgemaster.BuildConfig
import com.davinciapps.fridgemaster.data.api.GoogleAPIService
import com.davinciapps.fridgemaster.data.api.OpenAPIService
import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.param.res.openapi.IngResponse
import com.davinciapps.fridgemaster.data.param.res.openapi.ProcResponse
import com.davinciapps.fridgemaster.data.repository.dataSource.RecipeRemoteDataSource
import com.google.gson.Gson
import retrofit2.Response

class RecipeRemoteDataSourceImpl(
    private val openAPIService: OpenAPIService,
    private val googleAPIService: GoogleAPIService
): RecipeRemoteDataSource {
    private val TAG = RecipeRemoteDataSourceImpl::class.java.simpleName

    override suspend fun getRecommendedRecipes(params: HashMap<String, Any>): RestResponse? {
        val bodyParams = Gson().toJson(params).toByteArray()

        val request = RestOptions.builder()
            .addPath("/recommand")
            .addBody(bodyParams)
            .build()

        try {
            val response = Amplify.API.post(request, "everysearch")

            if(response.code.isSuccessful) {
                return response
            } else {
                return null
            }
        } catch (error: ApiException) {
            Log.e(TAG, "POST failed", error)
            return null
        }
    }

    override suspend fun getSearchedRecipes(params: HashMap<String, Any>): RestResponse? {
        val bodyParams = Gson().toJson(params).toByteArray()

        val request = RestOptions.builder()
            .addPath("/search")
            .addBody(bodyParams)
            .build()

        try {
            val response = Amplify.API.post(request, "everysearch")

            if(response.code.isSuccessful) {
                return response
            } else {
                return null
            }
        } catch (error: ApiException) {
            Log.e(TAG, "POST failed", error)
            return null
        }
    }

    override suspend fun getRecipeIngredients(recipeId: String): Response<IngResponse> {
        return openAPIService.getIngredients(recipeId)
    }

    override suspend fun getRecipeProcedures(recipeId: String): Response<ProcResponse> {
        return openAPIService.getProcedures(recipeId)
    }

    private val sites = listOf("10000recipe.com", "tistory.net")

    override suspend fun getRecommendWebRecipes(items: List<FreezerItem>): Response<GoogleSearchResponse> {
        return googleAPIService.getSearchResult(
            items.joinToString(" OR ") { "intext:\"${it.name}\"" } + " (" + sites.joinToString(" OR ") { "site:$it" } + ")",
            BuildConfig.GOOGLE_SEARCH_ENGINE,
            BuildConfig.GOOGLE_API_KEY,
            "countryKR",
            "레시피",
            1,
            "lang_ko",
            10
        )
    }
}