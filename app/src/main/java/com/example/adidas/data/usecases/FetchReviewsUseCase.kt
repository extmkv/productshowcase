package com.example.adidas.data.usecases

import com.example.adidas.data.repository.reviews.ReviewsRepository
import javax.inject.Inject

/**
 * A use-case to load the reviews from API.
 * @author Malik Dawar
 */
class FetchReviewsUseCase @Inject constructor(private val repository: ReviewsRepository) {

    suspend operator fun invoke(productId: String) = repository.fetchReviews(productId)
}