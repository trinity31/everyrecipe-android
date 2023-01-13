package com.example.everyrecipe.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.everyrecipe.data.model.FreezerItem

@Dao
interface FreezerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(freezerItem: FreezerItem)
}