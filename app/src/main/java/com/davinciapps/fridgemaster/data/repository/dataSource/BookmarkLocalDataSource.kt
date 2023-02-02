package com.davinciapps.fridgemaster.data.repository.dataSource

import com.davinciapps.fridgemaster.data.model.Recipe

interface BookmarkLocalDataSource {
    suspend fun saveBookmarkToDB(item: Recipe): Long
    suspend fun removeBookmarkFromDB(item: Recipe): Int
    suspend fun getBookmarks(): List<Recipe>
    suspend fun getItemById(id: String): Recipe?
}