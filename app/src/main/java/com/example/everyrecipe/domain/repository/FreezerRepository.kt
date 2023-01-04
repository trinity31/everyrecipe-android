package com.example.everyrecipe.domain.repository

import com.example.everyrecipe.data.model.FreezerItem
import kotlinx.coroutines.flow.Flow

interface FreezerRepository {
    suspend fun setFreezerItems(items: List<FreezerItem>)
    fun getFreezerItems(): Flow<List<FreezerItem>>
}