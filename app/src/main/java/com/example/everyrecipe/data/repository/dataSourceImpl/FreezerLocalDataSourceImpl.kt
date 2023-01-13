package com.example.everyrecipe.data.repository.dataSourceImpl

import com.example.everyrecipe.data.db.FreezerDAO
import com.example.everyrecipe.data.model.Food
import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.repository.dataSource.FreezerLocalDataSource

class FreezerLocalDataSourceImpl(private val freezerDAO: FreezerDAO): FreezerLocalDataSource {
    override suspend fun saveFreezerItemToDB(freezerItem: FreezerItem) {
        freezerDAO.insert(freezerItem)
    }
}