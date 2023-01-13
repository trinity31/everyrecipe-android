package com.example.everyrecipe.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.everyrecipe.data.model.FreezerItem

@Database(
    entities = [FreezerItem::class],
    version = 1,
    exportSchema = false
)
abstract class FreezerDatabase: RoomDatabase() {
 abstract fun getFreezerDAO(): FreezerDAO
}