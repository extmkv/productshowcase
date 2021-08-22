package com.example.adidas.data.usecases

import com.example.adidas.data.repository.product.ProductsRepository
import javax.inject.Inject

/**
 * A use-case to load the photos from Unsplash API.
 * @author Malik Dawar
 */
class FetchProductsUseCase @Inject constructor(private val repository: ProductsRepository) {

    suspend operator fun invoke() = repository.loadProducts()
}