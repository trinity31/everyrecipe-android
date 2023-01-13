package com.example.everyrecipe.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.everyrecipe.data.db.FreezerDAO
import com.example.everyrecipe.data.db.FreezerDatabase
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
}