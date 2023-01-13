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
    override suspend fun setFreezerItems(items: List<FreezerItem>) {
        freezerLocalDataSource.saveFreezerItemsToDB(items)
    }

    override suspend fun getFreezerItems(): Resource<List<FreezerItem>> {
        val items = freezerLocalDataSource.getFreezerItems()
        return Resource.Success(items)
    }

    override suspend fun removeFreezerItems(items: List<FreezerItem>) {
        val response = freezerLocalDataSource.removeFreezerItemsToDB(items)
    }
}