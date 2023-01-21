package com.example.everyrecipe.data.db

import androidx.room.*
import com.example.everyrecipe.data.model.Recipe

@Dao
interface BookmarkDAO {
    @Query("SELECT * from bookmarks")
    suspend fun getAllItems(): List<Recipe>

    @Query("SELECT * from bookmarks WHERE id = :id")
    suspend fun getItemById(id: String): Recipe?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Recipe): Long

    @Delete
    suspend fun delete(item: Recipe): Int
}