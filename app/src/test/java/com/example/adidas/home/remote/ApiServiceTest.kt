package com.example.adidas.home.remote

import com.example.adidas.ApiAbstract
import com.example.adidas.StringGenerator
import com.example.adidas.TestCoroutineRule
import com.example.adidas.data.remote.ProductApiInterface
import com.example.adidas.data.remote.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class ApiServiceTest : ApiAbstract<ProductApiInterface>() {

    private lateinit var productApiService: ProductApiInterface

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        productApiService = createService(ProductApiInterface::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun `given ApiInterface when the loadProducts is triggered then it returns the items`() =
        runBlocking {
            // Given
            enqueueResponse("products_response_200.json")

            // Invoke
            val response = productApiService.loadProducts()
            val responseBody = requireNotNull((response as ApiResponse.ApiSuccessResponse).data)
            mockWebServer.takeRequest()

            // Then
            responseBody.forEach {
                assertThat(it.id, `is`("HI333"))
                assertThat(it.currency, `is`("$"))
                assertThat(it.price, `is`(44))
                assertThat(it.name, `is`("Lorem ipsum"))
                assertThat(
                    it.description,
                    `is`("Lorem ipsum dolor sit amet, consectetur adipiscing elit")
                )
                assertThat(
                    it.imgUrl,
                    `is`("https://assets.adidas.com/images/w_320,h_320,f_auto,q_auto:sensitive,fl_lossy/c7099422ccc14e44b406abec00ba6c96_9366/NMD_R1_V2_Shoes_Black_FY6862_01_standard.jpg")
                )
            }
        }
}
