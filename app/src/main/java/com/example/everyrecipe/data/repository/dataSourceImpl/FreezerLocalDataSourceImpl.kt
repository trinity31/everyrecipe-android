package com.example.everyrecipe.data.repository.dataSourceImpl

import com.example.everyrecipe.data.db.FreezerDAO
import com.example.everyrecipe.data.model.Food
import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.repository.dataSource.FreezerLocalDataSource

class FreezerLocalDataSourceImpl(private val freezerDAO: FreezerDAO): FreezerLocalDataSource {
    override suspend fun saveFreezerItemsToDB(items: List<FreezerItem>) {
        freezerDAO.insertMultipleItems(items)
    }

    override suspend fun removeFreezerItemsToDB(items: List<FreezerItem>) {
        freezerDAO.deleteMultipleItems(items)
    }
}