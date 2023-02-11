package com.davinciapps.fridgemaster.data.param.res.google

data class GoogleSearchResponse(
    val context: Context,
    val items: List<RecipeItem>,
    val kind: String,
    val queries: Queries,
    val searchInformation: SearchInformation,
    val url: Url
)