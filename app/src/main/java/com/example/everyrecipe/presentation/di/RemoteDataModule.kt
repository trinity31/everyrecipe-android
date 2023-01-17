package com.example.everyrecipe.presentation.di

import com.example.everyrecipe.data.repository.dataSource.FoodRemoteDataSource
import com.example.everyrecipe.data.repository.dataSource.RecipeRemoteDataSource
import com.example.everyrecipe.data.repository.dataSourceImpl.FoodRemoteDataSourceImpl
import com.example.everyrecipe.data.repository.dataSourceImpl.RecipeRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideFoodRemoteDataSource(): FoodRemoteDataSource {
        return FoodRemoteDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideRecipeRemoteDataSource(): RecipeRemoteDataSource {
        return RecipeRemoteDataSourceImpl()
    }
}
