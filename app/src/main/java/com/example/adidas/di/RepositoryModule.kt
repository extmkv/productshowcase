package com.example.adidas.di

import com.example.adidas.data.remote.ProductApiInterface
import com.example.adidas.data.remote.ReviewApiInterface
import com.example.adidas.data.repository.product.ProductsRepository
import com.example.adidas.data.repository.product.ProductsRepositoryImpl
import com.example.adidas.data.repository.reviews.ReviewsRepository
import com.example.adidas.data.repository.reviews.ReviewsRepositoryImpl
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
    fun provideProductRepository(productApiService: ProductApiInterface): ProductsRepository {
        return ProductsRepositoryImpl(productApiService)
    }

    @Singleton
    @Provides
    fun provideReviewRepository(reviewApiInterface: ReviewApiInterface): ReviewsRepository {
        return ReviewsRepositoryImpl(reviewApiInterface)
    }
}
