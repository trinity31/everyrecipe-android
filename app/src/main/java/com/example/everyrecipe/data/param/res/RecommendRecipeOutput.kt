package com.example.everyrecipe.data.param.res

import com.example.everyrecipe.data.model.Recipe
import com.example.everyrecipe.data.model.RecipeInfo

data class RecommendRecipeOutput (
    var result: String = "",
    var body: List<Recipe> = listOf()
)