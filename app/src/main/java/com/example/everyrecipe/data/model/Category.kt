package com.example.everyrecipe.data.model

data class Category(
    val id: String,
    val name: String,
    val order: Int,
    val vegType: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String,
    val foods: List<Food>,
    val nextToken: String?
)