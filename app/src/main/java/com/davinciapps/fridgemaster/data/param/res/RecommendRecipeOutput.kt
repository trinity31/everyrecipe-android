package com.davinciapps.fridgemaster.data.param.res

import com.davinciapps.fridgemaster.data.model.Recipe

data class RecommendRecipeOutput (
    var result: String = "",
    var body: List<Recipe> = listOf()
)