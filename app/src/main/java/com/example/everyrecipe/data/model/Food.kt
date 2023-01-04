package com.example.everyrecipe.data.model

data class Food(
    val id: String,
    val categoryID: String,
    val name: String,
    val description: Any,
    val createdAt: String,
    val updatedAt: String
)