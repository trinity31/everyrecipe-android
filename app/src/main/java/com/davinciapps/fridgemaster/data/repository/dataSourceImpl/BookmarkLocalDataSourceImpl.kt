package com.davinciapps.fridgemaster.data.repository.dataSourceImpl

import com.davinciapps.fridgemaster.data.db.BookmarkDAO
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.data.repository.dataSource.BookmarkLocalDataSource

class BookmarkLocalDataSourceImpl(
    private val bookmarkDAO: BookmarkDAO
): BookmarkLocalDataSource {
    override suspend fun saveBookmarkToDB(item: Recipe): Long {
        return bookmarkDAO.insert(item)
    }

    override suspend fun removeBookmarkFromDB(item: Recipe): Int {
        return bookmarkDAO.delete(item)
    }

    override suspend fun getBookmarks(): List<Recipe> {
        return bookmarkDAO.getAllItems()
    }

    override suspend fun getItemById(id: String): Recipe? {
        return bookmarkDAO.getItemById(id)
    }
}