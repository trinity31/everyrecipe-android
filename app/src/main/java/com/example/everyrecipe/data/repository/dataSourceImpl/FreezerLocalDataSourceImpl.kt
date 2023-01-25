package com.example.everyrecipe.data.repository.dataSourceImpl

import com.example.everyrecipe.data.db.FreezerDAO
import com.example.everyrecipe.data.model.Food
import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.repository.dataSource.FreezerLocalDataSource
import kotlinx.coroutines.flow.Flow

class FreezerLocalDataSourceImpl(private val freezerDAO: FreezerDAO): FreezerLocalDataSource {
    override suspend fun saveFreezerItemsToDB(items: List<FreezerItem>): Boolean {
        return freezerDAO.insertMultipleItems(items)
    }

    override suspend fun removeFreezerItemsToDB(items: List<FreezerItem>) {
        freezerDAO.deleteMultipleItems(items)
    }

    override suspend fun getFreezerItems(): List<FreezerItem> {
        return freezerDAO.getAllItems()
    }

    override suspend fun getItemsByCategory(categoryId: String): List<FreezerItem> {
        return freezerDAO.getItemsByCategory(categoryId)
    }

    override suspend fun updateFreezerItem(item: FreezerItem): Boolean {
        return freezerDAO.update(item) > 0
    }

    override suspend fun getFreezerExistingItems(): List<FreezerItem> {
        return freezerDAO.getItemsExisting()
    }
}