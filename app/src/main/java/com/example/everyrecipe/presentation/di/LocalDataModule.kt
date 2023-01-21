package com.example.everyrecipe.presentation.di

import com.example.everyrecipe.data.db.BookmarkDAO
import com.example.everyrecipe.data.db.FreezerDAO
import com.example.everyrecipe.data.repository.dataSource.BookmarkLocalDataSource
import com.example.everyrecipe.data.repository.dataSource.FreezerLocalDataSource
import com.example.everyrecipe.data.repository.dataSourceImpl.BookmarkLocalDataSourceImpl
import com.example.everyrecipe.data.repository.dataSourceImpl.FreezerLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideFreezerLocalDataSource(freezerDAO: FreezerDAO): FreezerLocalDataSource {
        return FreezerLocalDataSourceImpl(freezerDAO)
    }

    @Singleton
    @Provides
    fun provideBookmarkLocalDataSource(bookmarkDAO: BookmarkDAO): BookmarkLocalDataSource {
        return BookmarkLocalDataSourceImpl(bookmarkDAO)
    }
}