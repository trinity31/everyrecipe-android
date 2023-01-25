package com.example.everyrecipe.data.repository

import com.amplifyframework.datastore.generated.model.Food
import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.repository.dataSource.FreezerLocalDataSource
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.domain.repository.FreezerRepository
import kotlinx.coroutines.flow.Flow

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