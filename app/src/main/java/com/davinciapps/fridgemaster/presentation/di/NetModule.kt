package com.davinciapps.fridgemaster.presentation.di

import com.davinciapps.fridgemaster.BuildConfig
import com.davinciapps.fridgemaster.data.api.OpenAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideOpenAPIService(retrofit: Retrofit):OpenAPIService {
        return retrofit.create(OpenAPIService::class.java)
    }
}