package com.example.everyrecipe.domain.repository

import com.example.everyrecipe.data.model.*
import com.example.everyrecipe.data.param.req.ReqParamRecommendRecipe
import com.example.everyrecipe.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecommendedRecipes(reqParam: ReqParamRecommendRecipe): Resource<List<Recipe>>
    suspend fun getSearchedRecipes(keywords: List<FreezerItem>): Resource<List<Recipe>>
    suspend fun getBookmarkedRecipes(): Resource<List<Recipe>>
    suspend fun saveRecipeToBookmark(recipe: Recipe)
    suspend fun deleteRecipeFromBookmark(recipe: Recipe)
    suspend fun getRecipeIngredients(recipeId: String): Resource<List<Ingredient>>
    suspend fun getRecipeProcedures(recipeId: String): Resource<List<Procedure>>
}