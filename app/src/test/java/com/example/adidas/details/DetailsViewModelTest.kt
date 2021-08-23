package com.example.adidas.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.adidas.StringGenerator
import com.example.adidas.TestCoroutineRule
import com.example.adidas.data.DataState
import com.example.adidas.data.ReviewDataSource
import com.example.adidas.data.model.ProductModel
import com.example.adidas.data.model.ReviewModel
import com.example.adidas.data.repository.reviews.ReviewsRepository
import com.example.adidas.data.usecases.FetchReviewsUseCase
import com.example.adidas.data.usecases.SubmitReviewUseCase
import com.example.adidas.ui.details.ContentState
import com.example.adidas.ui.details.DetailsUiState
import com.example.adidas.ui.details.DetailsViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DetailsViewModelTest {

    // Subject under test
    private lateinit var sut: DetailsViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestCoroutineRule()

    @MockK
    val fetchReviewsUseCase = mockk<FetchReviewsUseCase>(relaxed = true)

    @MockK
    val submitReviewUseCase = mockk<SubmitReviewUseCase>(relaxed = true)

    @MockK
    val reviewRepository = mockk<ReviewsRepository>(relaxed = true)

    @Before
    fun setUp() {
        sut = DetailsViewModel(fetchReviewsUseCase, submitReviewUseCase)
        MockKAnnotations.init(this)
    }

    @Test
    fun `when the viewModel is created and the fetchReviewsUseCase is invoked then fetchReviews in the reviewRepository is called to fetch reviews`() {
        coEvery { fetchReviewsUseCase.invoke(StringGenerator.getRandomString(10)) } returns flowOf(
            mockk()
        )

        coEvery { reviewRepository.fetchReviews(StringGenerator.getRandomString(10)) } returns flowOf(
            mockk()
        )

        runBlocking {
            sut.fetchProductReviews()
        }

        coEvery { reviewRepository.fetchReviews(StringGenerator.getRandomString(10)) }
    }


    @Test
    fun `when the submitProductReviews in viewModel is created and the submitReviewUseCase is invoked then submitReview in the reviewRepository is called to submit review`() {

        runBlocking {
            sut.submitProductReviews(mockk<ReviewModel>())
        }

        coEvery {
            submitReviewUseCase.invoke(
                StringGenerator.getRandomString(10),
                mockk<ReviewModel>()
            )
        } returns flowOf(
            mockk()
        )

        coEvery {
            reviewRepository.submitReview(
                StringGenerator.getRandomString(10),
                mockk<ReviewModel>()
            )
        } returns flowOf(
            mockk()
        )
    }
}
