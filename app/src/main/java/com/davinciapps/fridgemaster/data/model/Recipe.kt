package com.davinciapps.fridgemaster.data.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

@Entity(
    tableName = "bookmarks"
)
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Int,
    @Embedded
    val recipe: RecipeInfo?,
    @TypeConverters(ListStringTypeConverter::class)
    val ingredients: List<String>
) : Serializable

data class RecipeList(
    val recipes: List<Recipe>
) : Serializable

