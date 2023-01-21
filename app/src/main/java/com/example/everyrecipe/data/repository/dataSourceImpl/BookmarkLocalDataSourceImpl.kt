package com.example.everyrecipe.data.repository.dataSourceImpl

import com.example.everyrecipe.data.db.BookmarkDAO
import com.example.everyrecipe.data.model.Recipe
import com.example.everyrecipe.data.repository.dataSource.BookmarkLocalDataSource

class BookmarkLocalDataSourceImpl(
    private val bookmarkDAO: BookmarkDAO
): BookmarkLocalDataSource {
    override suspend fun saveBookmarkToDB(item: Recipe) {
        bookmarkDAO.insert(item)
    }

    override suspend fun removeBookmarkFromDB(item: Recipe) {
        bookmarkDAO.delete(item)
    }

    override suspend fun getBookmarks(): List<Recipe> {
        return bookmarkDAO.getAllItems()
    }
}