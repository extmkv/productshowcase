package com.example.adidas.data.remote

import com.example.adidas.data.model.ProductModel
import retrofit2.http.GET

interface ApiInterface {

    @GET(SERVICE_API_PRODUCT)
    suspend fun loadProducts(
    ): ApiResponse<ArrayList<ProductModel>>

    companion object {
        const val SERVICE_API_PRODUCT = "product"
    }
}