package com.example.everyrecipe.data.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

@Entity(
    tableName = "bookmarks"
)
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Int? = null,
    @Embedded
    val recipe: RecipeInfo?,
    @TypeConverters(ListStringTypeConverter::class)
    val ingredients: List<String>
) : Serializable

data class RecipeList(
    val recipes: List<Recipe>
) : Serializable

class ListStringTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(list, type)
    }
}
