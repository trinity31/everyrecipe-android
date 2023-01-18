package com.example.everyrecipe.data.repository.dataSourceImpl

import android.util.Log
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.rest.RestOptions
import com.amplifyframework.api.rest.RestResponse
import com.amplifyframework.kotlin.core.Amplify
import com.example.everyrecipe.data.repository.dataSource.RecipeRemoteDataSource
import com.google.gson.Gson

class RecipeRemoteDataSourceImpl(): RecipeRemoteDataSource {
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
}