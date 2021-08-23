package com.example.adidas.data.usecases

import com.example.adidas.data.model.ReviewModel
import com.example.adidas.data.repository.reviews.ReviewsRepository
import javax.inject.Inject

/**
 * A use-case to submit the review to API.
 * @author Malik Dawar
 */
class SubmitReviewUseCase @Inject constructor(private val repository: ReviewsRepository) {

    suspend operator fun invoke(productId: String, review: ReviewModel) =
        repository.submitReview(productId, review)
}