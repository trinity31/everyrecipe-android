package com.davinciapps.fridgemaster.presentation.di

import com.davinciapps.fridgemaster.data.db.BookmarkDAO
import com.davinciapps.fridgemaster.data.db.FreezerDAO
import com.davinciapps.fridgemaster.data.repository.dataSource.BookmarkLocalDataSource
import com.davinciapps.fridgemaster.data.repository.dataSource.FreezerLocalDataSource
import com.davinciapps.fridgemaster.data.repository.dataSourceImpl.BookmarkLocalDataSourceImpl
import com.davinciapps.fridgemaster.data.repository.dataSourceImpl.FreezerLocalDataSourceImpl
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