package com.example.everyrecipe.data.repository.dataSource

import com.example.everyrecipe.data.model.FreezerItem

interface FreezerLocalDataSource {
    suspend fun saveFreezerItemsToDB(items: List<FreezerItem>)
    suspend fun removeFreezerItemsToDB(items: List<FreezerItem>)
}