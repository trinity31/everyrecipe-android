package com.example.everyrecipe.data.repository.dataSource

import com.example.everyrecipe.data.model.Recipe

interface BookmarkLocalDataSource {
    suspend fun saveBookmarkToDB(item: Recipe): Long
    suspend fun removeBookmarkFromDB(item: Recipe): Int
    suspend fun getBookmarks(): List<Recipe>
    suspend fun getItemById(id: String): Recipe?
}