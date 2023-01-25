package com.example.everyrecipe.domain.repository

import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface FreezerRepository {
    suspend fun setFreezerItems(items: List<FreezerItem>): Boolean
    suspend fun getFreezerItems(): Resource<List<FreezerItem>>
    suspend fun removeFreezerItems(items: List<FreezerItem>)
    suspend fun getItemsByCategory(categoryId: String): Resource<List<FreezerItem>>
    suspend fun updateFreezerItem(item: FreezerItem): Boolean
    suspend fun getFreezerExistingItems(): Resource<List<FreezerItem>>
}