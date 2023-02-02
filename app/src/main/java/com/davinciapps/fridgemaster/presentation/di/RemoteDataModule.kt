package com.davinciapps.fridgemaster.presentation.di

import com.davinciapps.fridgemaster.data.api.OpenAPIService
import com.davinciapps.fridgemaster.data.repository.dataSource.FoodRemoteDataSource
import com.davinciapps.fridgemaster.data.repository.dataSource.RecipeRemoteDataSource
import com.davinciapps.fridgemaster.data.repository.dataSourceImpl.FoodRemoteDataSourceImpl
import com.davinciapps.fridgemaster.data.repository.dataSourceImpl.RecipeRemoteDataSourceImpl
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
    fun provideRecipeRemoteDataSource(
        openAPIService: OpenAPIService
    ): RecipeRemoteDataSource {
        return RecipeRemoteDataSourceImpl(openAPIService)
    }
}
