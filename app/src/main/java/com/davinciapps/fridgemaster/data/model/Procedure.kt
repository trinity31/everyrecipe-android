package com.davinciapps.fridgemaster.data.model

import java.io.Serializable

data class Procedure(
    val COOKING_DC: String,
    val COOKING_NO: Int,
    val RECIPE_ID: Int,
    val ROW_NUM: Int,
    val STEP_TIP: String
): Serializable

data class ProcedureList(
    val procedures: List<Procedure>
): Serializable