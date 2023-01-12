package com.example.everyrecipe.data.model

import com.amplifyframework.core.model.Model

data class Food(
    val id: String,
    val categoryID: String,
    val name: String,
    val description: Any,
    val createdAt: String,
    val updatedAt: String
)