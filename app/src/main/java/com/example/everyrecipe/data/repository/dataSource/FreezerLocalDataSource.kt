package com.example.everyrecipe.data.repository.dataSource

import com.example.everyrecipe.data.model.FreezerItem

interface FreezerLocalDataSource {
    suspend fun saveFreezerItemToDB(freezerItem: FreezerItem)
}