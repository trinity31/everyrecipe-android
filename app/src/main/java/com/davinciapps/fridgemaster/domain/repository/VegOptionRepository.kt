package com.davinciapps.fridgemaster.domain.repository

interface VegOptionRepository {
    suspend fun getVegOption(): String
    suspend fun setVegOption(option: String)
}