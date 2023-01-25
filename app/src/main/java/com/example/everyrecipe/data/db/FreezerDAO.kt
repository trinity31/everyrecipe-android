package com.example.everyrecipe.data.db

import androidx.room.*
import com.example.everyrecipe.data.model.FreezerItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FreezerDAO {
    @Query("SELECT * from freezerItems")
    suspend fun getAllItems(): List<FreezerItem>

    @Transaction
    suspend fun insertMultipleItems(items: List<FreezerItem>): Boolean {
        items.forEach {
            insert(it)
        }
        return true
    }

    @Transaction
    suspend fun deleteMultipleItems(items: List<FreezerItem>) {
        items.forEach {
            delete(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FreezerItem): Long

    @Update
    suspend fun update(item: FreezerItem): Int

    @Delete
    suspend fun delete(item: FreezerItem): Int

    @Query("SELECT * FROM freezerItems WHERE categoryID = :category")
    suspend fun getItemsByCategory(category: String): List<FreezerItem>

    @Query("SELECT * FROM freezerItems WHERE exist = 1")
    suspend fun getItemsExisting(): List<FreezerItem>
}