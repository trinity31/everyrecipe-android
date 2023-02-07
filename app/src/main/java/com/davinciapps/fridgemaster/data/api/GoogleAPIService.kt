package com.davinciapps.fridgemaster.data.api

import GoogleSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleAPIService {
    @GET("customsearch/v1")
    suspend fun getSearchResult(
        @Query("q") query: String,
        @Query("cx") cx: String,
        @Query("key") key: String,
        @Query("cr") cr: String,
        @Query("exactTerms") exactTerms: String,
        @Query("filter") filter: Int,
        @Query("lr") lr: String,
        @Query("num") num: Int
    ): Response<GoogleSearchResponse>
}