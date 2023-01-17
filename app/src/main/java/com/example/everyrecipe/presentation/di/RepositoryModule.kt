package com.example.everyrecipe.presentation.di

import com.example.everyrecipe.data.repository.FoodRepositoryImpl
import com.example.everyrecipe.data.repository.FreezerRepositoryImpl
import com.example.everyrecipe.data.repository.RecipeRepositoryImpl
import com.example.everyrecipe.data.repository.dataSource.FoodRemoteDataSource
import com.example.everyrecipe.data.repository.dataSource.FreezerLocalDataSource
import com.example.everyrecipe.data.repository.dataSource.RecipeRemoteDataSource
import com.example.everyrecipe.domain.repository.FoodRepository
import com.example.everyrecipe.domain.repository.FreezerRepository
import com.example.everyrecipe.domain.repository.RecipeRepository
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
        recipeRemoteDataSource: RecipeRemoteDataSource
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeRemoteDataSource)
    }
}