package com.davinciapps.fridgemaster.data.api

import com.davinciapps.fridgemaster.data.param.res.openapi.IngResponse
import com.davinciapps.fridgemaster.data.param.res.openapi.ProcResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenAPIService {
    @GET("json/Grid_20150827000000000227_1/1/100")
    suspend fun getIngredients(
        @Query("RECIPE_ID")
        recipeId:String
    ): Response<IngResponse>

    @GET("json/Grid_20150827000000000228_1/1/10")
    suspend fun getProcedures(
        @Query("RECIPE_ID")
        recipeId:String
    ): Response<ProcResponse>
}