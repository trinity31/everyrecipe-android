package com.davinciapps.fridgemaster.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.davinciapps.fridgemaster.data.db.BookmarkDAO
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.data.model.ListStringTypeConverter

@Database(
    entities = [Recipe::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListStringTypeConverter::class)
abstract class BookmarkDatabase: RoomDatabase() {
    abstract fun getBookmarkDAO(): BookmarkDAO
}