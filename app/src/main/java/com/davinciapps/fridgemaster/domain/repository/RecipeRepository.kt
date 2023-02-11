package com.davinciapps.fridgemaster.domain.repository

import com.davinciapps.fridgemaster.data.model.*
import com.davinciapps.fridgemaster.data.param.req.ReqParamSearchRecipe
import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.model.Ingredient
import com.davinciapps.fridgemaster.data.model.Procedure
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.data.util.Resource

interface RecipeRepository {
    suspend fun getRecommendedRecipes(reqParam: ReqParamSearchRecipe): Resource<List<Recipe>>
    suspend fun getSearchedRecipes(reqParam: ReqParamSearchRecipe): Resource<List<Recipe>>
    suspend fun getBookmarkedRecipes(): Resource<List<Recipe>>
    suspend fun getItemById(id: String): Resource<Recipe?>
    suspend fun saveRecipeToBookmark(recipe: Recipe): Long
    suspend fun deleteRecipeFromBookmark(recipe: Recipe): Int
    suspend fun getRecipeIngredients(recipeId: String): Resource<List<Ingredient>>
    suspend fun getRecipeProcedures(recipeId: String): Resource<List<Procedure>>

    suspend fun getRecommendedWebRecipes(items: List<FreezerItem>): Resource<List<Recipe>>
}