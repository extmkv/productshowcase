package com.example.adidas.home

import com.example.adidas.data.DataState
import com.example.adidas.data.ProductDataSource
import com.example.adidas.data.repository.product.ProductsRepository
import com.example.adidas.data.usecases.FetchProductsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchProductsUseCaseTest {

    @MockK
    private lateinit var repository: ProductsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `given when the invoke in FetchProductsUseCase is called then it fetch the products`() = runBlocking {
        // Given
        val sut = FetchProductsUseCase(repository)
        val givenProducts = ProductDataSource.getProductList()

        // When
        coEvery { repository.loadProducts() }
            .returns(flowOf(DataState.success(givenProducts)))

        // Invoke
        val productListFlow = sut()

        // Then
        MatcherAssert.assertThat(productListFlow, CoreMatchers.notNullValue())

        val productListDataState = productListFlow.first()
        MatcherAssert.assertThat(productListDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            productListDataState,
            CoreMatchers.instanceOf(DataState.Success::class.java)
        )

        val productList = (productListDataState as DataState.Success).data
        MatcherAssert.assertThat(productList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(productList.size, `is`(givenProducts.size))
    }
}
