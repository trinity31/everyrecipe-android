package com.davinciapps.fridgemaster.data.repository.dataSourceImpl

import com.davinciapps.fridgemaster.data.db.FreezerDAO
import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.repository.dataSource.FreezerLocalDataSource

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