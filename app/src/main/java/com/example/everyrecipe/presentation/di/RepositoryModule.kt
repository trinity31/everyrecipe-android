package com.example.everyrecipe.presentation.di

import com.example.everyrecipe.data.repository.FoodRepositoryImpl
import com.example.everyrecipe.data.repository.dataSource.FoodRemoteDataSource
import com.example.everyrecipe.domain.repository.FoodRepository
import com.example.everyrecipe.domain.repository.FreezerRepository
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

//    @Singleton
//    @Provides
//    fun provideFreezerRepository(
//        freezerRepository: FreezerRepository
//    ): FreezerRepository {
//        return FreezerRepositoryImpl(freezerRepository)
//    }
}