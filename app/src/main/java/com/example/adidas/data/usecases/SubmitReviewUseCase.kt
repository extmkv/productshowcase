package com.example.adidas.data.usecases

import com.example.adidas.data.model.ReviewModel
import com.example.adidas.data.repository.reviews.ReviewsRepository
import javax.inject.Inject

/**
 * A use-case to load the photos from Unsplash API.
 * @author Malik Dawar
 */
class SubmitReviewUseCase @Inject constructor(private val repository: ReviewsRepository) {

    suspend operator fun invoke(productId: String, review: ReviewModel) =
        repository.submitReviews(productId, review)
}