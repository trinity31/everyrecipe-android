package com.example.everyrecipe.data.repository

import com.amplifyframework.api.rest.RestResponse
import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.model.Ingredient
import com.example.everyrecipe.data.model.Procedure
import com.example.everyrecipe.data.model.Recipe
import com.example.everyrecipe.data.param.req.ReqParamSearchRecipe
import com.example.everyrecipe.data.param.res.RecommendRecipeOutput
import com.example.everyrecipe.data.repository.dataSource.RecipeRemoteDataSource
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.domain.repository.RecipeRepository
import com.google.gson.Gson

class RecipeRepositoryImpl(
    private val recipeRemoteDataSource: RecipeRemoteDataSource
): RecipeRepository {
    override suspend fun getRecommendedRecipes(reqParam: ReqParamSearchRecipe): Resource<List<Recipe>> {
        return responceToResource(recipeRemoteDataSource.getRecommendedRecipes(reqParam.getParamMap()))
    }

    private fun responceToResource(response: RestResponse?): Resource<List<Recipe>> {
        val recipeOutput = Gson().fromJson(response?.data?.asString(), RecommendRecipeOutput::class.java)

        return if(recipeOutput.result == "success") {
            Resource.Success(recipeOutput.body)
        } else {
            Resource.Error(response.toString())
        }
    }

    override suspend fun getSearchedRecipes(reqParam: ReqParamSearchRecipe): Resource<List<Recipe>> {
        return responceToResource(recipeRemoteDataSource.getSearchedRecipes(reqParam.getParamMap()))
    }

    override suspend fun getBookmarkedRecipes(): Resource<List<Recipe>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveRecipeToBookmark(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecipeFromBookmark(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipeIngredients(recipeId: String): Resource<List<Ingredient>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipeProcedures(recipeId: String): Resource<List<Procedure>> {
        TODO("Not yet implemented")
    }
}