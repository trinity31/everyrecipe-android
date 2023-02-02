package com.davinciapps.fridgemaster.data.repository

import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.repository.dataSource.FreezerLocalDataSource
import com.davinciapps.fridgemaster.data.util.Resource
import com.davinciapps.fridgemaster.domain.repository.FreezerRepository

class FreezerRepositoryImpl(
    private val freezerLocalDataSource: FreezerLocalDataSource
): FreezerRepository {
    override suspend fun setFreezerItems(items: List<FreezerItem>): Boolean {
        return freezerLocalDataSource.saveFreezerItemsToDB(items)
    }

    override suspend fun getFreezerItems(): Resource<List<FreezerItem>> {
        val items = freezerLocalDataSource.getFreezerItems()
        return Resource.Success(items)
    }

    override suspend fun removeFreezerItems(items: List<FreezerItem>) {
        freezerLocalDataSource.removeFreezerItemsToDB(items)
    }

    override suspend fun getItemsByCategory(categoryId: String): Resource<List<FreezerItem>> {
        val items = freezerLocalDataSource.getItemsByCategory(categoryId)
        return Resource.Success(items.sortedBy { it.name })
    }

    override suspend fun updateFreezerItem(item: FreezerItem): Boolean {
        return freezerLocalDataSource.updateFreezerItem(item)
    }

    override suspend fun getFreezerExistingItems(): Resource<List<FreezerItem>> {
        val items = freezerLocalDataSource.getFreezerExistingItems()
        return Resource.Success(items)
    }
}