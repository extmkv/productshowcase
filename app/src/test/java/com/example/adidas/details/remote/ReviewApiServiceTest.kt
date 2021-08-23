package com.example.adidas.details.remote

import com.example.adidas.ApiAbstract
import com.example.adidas.StringGenerator
import com.example.adidas.TestCoroutineRule
import com.example.adidas.data.ReviewDataSource
import com.example.adidas.data.remote.ApiResponse
import com.example.adidas.data.remote.ReviewApiInterface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class ReviewApiServiceTest : ApiAbstract<ReviewApiInterface>() {

    private lateinit var reviewApiInterface: ReviewApiInterface

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        reviewApiInterface = createService(ReviewApiInterface::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun `given ApiInterface when the loadReviews is triggered then it returns the items`() =
        runBlocking {
            // Given
            enqueueResponse("reviews_response_200.json")

            // Invoke
            val response = reviewApiInterface.fetchProductReviews(StringGenerator.getRandomString(10))
            val responseBody = requireNotNull((response as ApiResponse.ApiSuccessResponse).data)
            mockWebServer.takeRequest()

            // Then
            responseBody.forEach {
                assertThat(it.productId, `is`("HI333"))
                assertThat(it.rating, `is`(5))
                assertThat(it.text, `is`("hello this is test"))
                assertThat(it, `is`(ReviewDataSource.reviewModel))
            }
        }
}
