package com.example.everyrecipe.data.model

import java.io.Serializable

data class Recipe(
    val recipe: RecipeInfo?,
    val ingredients: List<String>
) : Serializable

data class RecipeList(
    val recipes: List<Recipe>
) : Serializable