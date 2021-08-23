package com.example.adidas.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.adidas.TestCoroutineRule
import com.example.adidas.data.DataState
import com.example.adidas.data.ProductDataSource
import com.example.adidas.data.model.ProductModel
import com.example.adidas.data.usecases.FetchProductsUseCase
import com.example.adidas.ui.home.ContentState
import com.example.adidas.ui.home.HomeUiState
import com.example.adidas.ui.home.HomeViewModel
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
class HomeViewModelTest {

    // Subject under test
    private lateinit var sut: HomeViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestCoroutineRule()

    @MockK
    lateinit var fetchProductsUseCase: FetchProductsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test when HomeViewModel is initialized, products are fetched`() = runBlocking {
        // Given
        val givenProducts = ProductDataSource.getProductList()
        val uiObserver = mockk<Observer<HomeUiState>>(relaxed = true)
        val productsListObserver = mockk<Observer<List<ProductModel>>>(relaxed = true)

        // When
        coEvery { fetchProductsUseCase.invoke() }
            .returns(flowOf(DataState.success(givenProducts)))

        // Invoke
        sut = HomeViewModel(fetchProductsUseCase)
        sut.uiStateLiveData.observeForever(uiObserver)
        sut.productListLiveData.observeForever(productsListObserver)

        // Then
        coVerify(exactly = 1) { fetchProductsUseCase.invoke() }
        verify { uiObserver.onChanged(match { it == ContentState }) }
        verify { productsListObserver.onChanged(match { it.size == givenProducts.size }) }
    }
}
