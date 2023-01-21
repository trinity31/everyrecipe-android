package com.example.everyrecipe.data.repository.dataSource

import com.example.everyrecipe.data.model.Recipe

interface BookmarkLocalDataSource {
    suspend fun saveBookmarkToDB(item: Recipe)
    suspend fun removeBookmarkFromDB(item: Recipe)
    suspend fun getBookmarks(): List<Recipe>
}