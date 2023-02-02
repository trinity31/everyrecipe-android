package com.davinciapps.fridgemaster.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davinciapps.fridgemaster.data.model.FreezerItem

@Database(
    entities = [FreezerItem::class],
    version = 1,
    exportSchema = false
)
abstract class FreezerDatabase: RoomDatabase() {
 abstract fun getFreezerDAO(): FreezerDAO
}