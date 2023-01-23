package com.example.everyrecipe.data.model

import java.io.Serializable

data class Ingredient(
    val IRDNT_CPCTY: String,
    val IRDNT_NM: String,
    val IRDNT_SN: Int,
    val IRDNT_TY_CODE: String,
    val IRDNT_TY_NM: String,
    val RECIPE_ID: Int,
    val ROW_NUM: Int
): Serializable

data class IngredientList(
    val ingredients: List<Ingredient>
): Serializable