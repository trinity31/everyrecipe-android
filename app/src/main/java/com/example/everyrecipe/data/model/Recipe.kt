package com.example.everyrecipe.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "bookmarks"
)
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val recipe: RecipeInfo?,
    val ingredients: List<String>
) : Serializable

data class RecipeList(
    val recipes: List<Recipe>
) : Serializable