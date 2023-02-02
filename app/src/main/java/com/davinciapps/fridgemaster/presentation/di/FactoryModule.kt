package com.davinciapps.fridgemaster.presentation.di

import android.app.Application
import com.davinciapps.fridgemaster.domain.repository.FoodRepository
import com.davinciapps.fridgemaster.domain.repository.FreezerRepository
import com.davinciapps.fridgemaster.domain.repository.RecipeRepository
import com.davinciapps.fridgemaster.presentation.viewmodel.*
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

    @Singleton
    @Provides
    fun provideSearchViewModelFactory(
        application: Application,
        foodRepository: FoodRepository,
        recipeRepository: RecipeRepository,
        freezerRepository: FreezerRepository
    ): SearchViewModelFactory {
        return SearchViewModelFactory(
            application,
            foodRepository,
            recipeRepository,
            freezerRepository
        )
    }

    @Singleton
    @Provides
    fun provideRecommendViewModelFactory(
        application: Application,
        recipeRepository: RecipeRepository
    ): RecommendViewModelFactory {
        return RecommendViewModelFactory(
            application,
            recipeRepository
        )
    }
}
