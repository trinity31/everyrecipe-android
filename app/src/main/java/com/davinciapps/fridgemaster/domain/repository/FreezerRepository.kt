package com.davinciapps.fridgemaster.domain.repository

import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.util.Resource

interface FreezerRepository {
    suspend fun setFreezerItems(items: List<FreezerItem>): Boolean
    suspend fun getFreezerItems(): Resource<List<FreezerItem>>
    suspend fun removeFreezerItems(items: List<FreezerItem>)
    suspend fun getItemsByCategory(categoryId: String): Resource<List<FreezerItem>>
    suspend fun updateFreezerItem(item: FreezerItem): Boolean
    suspend fun getFreezerExistingItems(): Resource<List<FreezerItem>>
}