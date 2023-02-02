package com.davinciapps.fridgemaster.presentation.di

import com.davinciapps.fridgemaster.data.repository.FoodRepositoryImpl
import com.davinciapps.fridgemaster.data.repository.FreezerRepositoryImpl
import com.davinciapps.fridgemaster.data.repository.RecipeRepositoryImpl
import com.davinciapps.fridgemaster.data.repository.dataSource.BookmarkLocalDataSource
import com.davinciapps.fridgemaster.data.repository.dataSource.FoodRemoteDataSource
import com.davinciapps.fridgemaster.data.repository.dataSource.FreezerLocalDataSource
import com.davinciapps.fridgemaster.data.repository.dataSource.RecipeRemoteDataSource
import com.davinciapps.fridgemaster.domain.repository.FoodRepository
import com.davinciapps.fridgemaster.domain.repository.FreezerRepository
import com.davinciapps.fridgemaster.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideFoodRepository(
        foodRemoteDataSource: FoodRemoteDataSource
    ): FoodRepository {
        return FoodRepositoryImpl(foodRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideFreezerRepository(
        freezerLocalDataSource: FreezerLocalDataSource
    ): FreezerRepository {
        return FreezerRepositoryImpl(freezerLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeRemoteDataSource: RecipeRemoteDataSource,
        bookmarkLocalDataSource: BookmarkLocalDataSource
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeRemoteDataSource, bookmarkLocalDataSource)
    }
}