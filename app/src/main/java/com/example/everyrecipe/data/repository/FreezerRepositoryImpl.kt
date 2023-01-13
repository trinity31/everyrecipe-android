package com.example.everyrecipe.data.repository

import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.repository.dataSource.FreezerLocalDataSource
import com.example.everyrecipe.domain.repository.FreezerRepository
import kotlinx.coroutines.flow.Flow

class FreezerRepositoryImpl(
    private val freezerLocalDataSource: FreezerLocalDataSource
): FreezerRepository {
    override suspend fun setFreezerItems(items: List<FreezerItem>) {
        items.forEach {
            freezerLocalDataSource.saveFreezerItemToDB(it)
        }
    }

    override fun getFreezerItems(): Flow<List<FreezerItem>> {
        TODO("Not yet implemented")
    }
}