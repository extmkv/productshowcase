package com.example.adidas.data.remote

import com.example.adidas.data.model.ProductModel
import com.example.adidas.data.model.ReviewModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiInterface {

    @GET(SERVICE_API_PRODUCT)
    suspend fun loadProducts(): ApiResponse<ArrayList<ProductModel>>

    companion object {
        const val SERVICE_API_PRODUCT = "product"
    }
}