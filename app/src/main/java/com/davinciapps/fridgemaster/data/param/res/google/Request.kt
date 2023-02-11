package com.davinciapps.fridgemaster.data.param.res.google
data class Request(
    val count: Int,
    val cr: String,
    val cx: String,
    val exactTerms: String,
    val filter: String,
    val inputEncoding: String,
    val language: String,
    val outputEncoding: String,
    val safe: String,
    val searchTerms: String,
    val startIndex: Int,
    val title: String,
    val totalResults: String
)