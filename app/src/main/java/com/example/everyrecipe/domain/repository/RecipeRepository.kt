package com.example.everyrecipe.domain.repository

import com.example.everyrecipe.data.model.*
import com.example.everyrecipe.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecommendedRecipes(): Flow<List<Recipe>>
    fun getSearchedRecipes(keywords: List<FreezerItem>): Flow<List<Recipe>>
    fun getBookmarkedRecipes(): Flow<List<Recipe>>
    suspend fun saveRecipeToBookmark(recipe: Recipe)
    suspend fun deleteRecipeFromBookmark(recipe: Recipe)
    fun getRecipeIngredients(recipeId: String): Resource<List<Ingredient>>
    fun getRecipeProcedures(recipeId: String): Resource<List<Procedure>>
}