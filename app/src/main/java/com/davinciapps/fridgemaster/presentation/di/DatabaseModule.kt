package com.davinciapps.fridgemaster.presentation.di

import android.app.Application
import androidx.room.Room
import com.davinciapps.fridgemaster.data.db.BookmarkDAO
import com.davinciapps.fridgemaster.data.db.BookmarkDatabase
import com.davinciapps.fridgemaster.data.db.FreezerDAO
import com.davinciapps.fridgemaster.data.db.FreezerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFreezerDatabase(app: Application): FreezerDatabase {
        return Room.databaseBuilder(app, FreezerDatabase::class.java, "freezer_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideFreezerDao(freezerDatabase: FreezerDatabase): FreezerDAO {
        return freezerDatabase.getFreezerDAO()
    }

    @Singleton
    @Provides
    fun provideBookmarkDatabase(app: Application): BookmarkDatabase {
        return Room.databaseBuilder(app, BookmarkDatabase::class.java, "bookmark_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBookmarkDAO(bookmarkDatabase: BookmarkDatabase): BookmarkDAO {
        return bookmarkDatabase.getBookmarkDAO()
    }
}