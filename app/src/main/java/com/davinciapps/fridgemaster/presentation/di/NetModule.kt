package com.davinciapps.fridgemaster.presentation.di

import com.davinciapps.fridgemaster.BuildConfig
import com.davinciapps.fridgemaster.data.api.GoogleAPIService
import com.davinciapps.fridgemaster.data.api.OpenAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    @Singleton
    @Provides
    @Named("OpenAPI")
    fun provideRetrofit1(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    @Named("GoogleAPI")
    fun provideRetrofit2(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.GOOGLE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideOpenAPIService1(@Named("OpenAPI") retrofit: Retrofit):OpenAPIService {
        return retrofit.create(OpenAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideOpenAPIService2(@Named("GoogleAPI") retrofit: Retrofit): GoogleAPIService {
        return retrofit.create(GoogleAPIService::class.java)
    }
}