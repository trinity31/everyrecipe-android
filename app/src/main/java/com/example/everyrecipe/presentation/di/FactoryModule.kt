package com.example.everyrecipe.presentation.di

import android.app.Application
import com.example.everyrecipe.domain.repository.FoodRepository
import com.example.everyrecipe.domain.repository.FreezerRepository
import com.example.everyrecipe.domain.repository.RecipeRepository
import com.example.everyrecipe.presentation.viewmodel.BookmarkViewModelFactory
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
    fun provideFreezerViewModelFactory(
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

    @Singleton
    @Provides
    fun provideBookmarkViewModelFactory(
        application: Application,
        recipeRepository: RecipeRepository
    ): BookmarkViewModelFactory {
        return BookmarkViewModelFactory(
            application,
            recipeRepository
        )
    }
}
