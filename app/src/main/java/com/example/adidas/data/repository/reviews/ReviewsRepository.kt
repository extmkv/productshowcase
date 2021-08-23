package com.example.adidas.data.repository.reviews

import com.example.adidas.data.DataState
import com.example.adidas.data.model.ProductModel
import com.example.adidas.data.model.ReviewModel
import kotlinx.coroutines.flow.Flow

/**
 * ImagineRepository is an interface data layer to handle communication with any data source such as Server or local database.
 * @see [ProductsRepositoryImpl] for implementation of this class to utilize Unsplash API.
 * @author Malik Dawar
 */
interface ReviewsRepository {
    suspend fun fetchReviews(productId: String): Flow<DataState<ArrayList<ReviewModel>>>

    suspend fun submitReviews(productId: String, review: ReviewModel): Flow<DataState<ReviewModel>>
}