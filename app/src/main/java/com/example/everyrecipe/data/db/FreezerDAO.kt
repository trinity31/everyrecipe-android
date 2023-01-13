package com.example.everyrecipe.data.db

import androidx.room.*
import com.example.everyrecipe.data.model.FreezerItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FreezerDAO {
    @Query("SELECT * from freezerItems")
    suspend fun getAllItems(): List<FreezerItem>

    @Transaction
    suspend fun insertMultipleItems(items: List<FreezerItem>) {
        items.forEach {
            insert(it)
        }
    }

    @Transaction
    suspend fun deleteMultipleItems(items: List<FreezerItem>) {
        items.forEach {
            delete(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: FreezerItem)

    @Delete
    suspend fun delete(item: FreezerItem)
}