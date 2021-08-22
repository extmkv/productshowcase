package com.example.adidas.di

import com.example.adidas.data.remote.ApiInterface
import com.example.adidas.data.repository.ProductsRepository
import com.example.adidas.data.repository.ProductsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The Dagger Module for providing repository instances.
 * @author Malik Dawar
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideImagineRepository(apiService: ApiInterface): ProductsRepository {
        return ProductsRepositoryImpl(apiService)
    }
}
