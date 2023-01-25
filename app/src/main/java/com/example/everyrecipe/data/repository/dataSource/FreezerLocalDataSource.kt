package com.example.everyrecipe.data.repository.dataSource

import com.example.everyrecipe.data.model.FreezerItem
import kotlinx.coroutines.flow.Flow

interface FreezerLocalDataSource {
    suspend fun saveFreezerItemsToDB(items: List<FreezerItem>): Boolean
    suspend fun removeFreezerItemsToDB(items: List<FreezerItem>)
    suspend fun getFreezerItems(): List<FreezerItem>
    suspend fun getItemsByCategory(categoryId: String): List<FreezerItem>
    suspend fun updateFreezerItem(item: FreezerItem): Boolean
    suspend fun getFreezerExistingItems(): List<FreezerItem>
}