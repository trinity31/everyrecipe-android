package com.example.everyrecipe.domain.repository

interface VegOptionRepository {
    suspend fun getVegOption(): String
    suspend fun setVegOption(option: String)
}