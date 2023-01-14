package com.example.everyrecipe.presentation.di

import android.app.Application
import com.example.everyrecipe.domain.repository.FoodRepository
import com.example.everyrecipe.domain.repository.FreezerRepository
import com.example.everyrecipe.presentation.viewmodel.FreezerViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        foodRepository: FoodRepository,
        freezerRepository: FreezerRepository
    ): FreezerViewModelFactory {
        return FreezerViewModelFactory(
            application,
            foodRepository,
            freezerRepository
        )
    }

}
