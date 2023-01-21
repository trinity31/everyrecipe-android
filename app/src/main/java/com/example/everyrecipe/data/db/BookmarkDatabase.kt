package com.example.everyrecipe.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.everyrecipe.data.model.ListStringTypeConverter
import com.example.everyrecipe.data.model.Recipe

@Database(
    entities = [Recipe::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListStringTypeConverter::class)
abstract class BookmarkDatabase: RoomDatabase() {
    abstract fun getBookmarkDAO(): BookmarkDAO
}