package com.example.adidas.data.repository

import com.example.adidas.data.DataState
import com.example.adidas.data.model.ProductModel
import kotlinx.coroutines.flow.Flow

/**
 * ImagineRepository is an interface data layer to handle communication with any data source such as Server or local database.
 * @see [ProductsRepositoryImpl] for implementation of this class to utilize Unsplash API.
 * @author Malik Dawar
 */
interface ProductsRepository {
    suspend fun loadProducts(): Flow<DataState<ArrayList<ProductModel>>>
}