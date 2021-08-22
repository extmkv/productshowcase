package com.example.adidas.data.repository

import androidx.annotation.WorkerThread
import com.example.adidas.core.extensions.noNetworkErrorMessage
import com.example.adidas.core.extensions.somethingWentWrong
import com.example.adidas.data.DataState
import com.example.adidas.data.model.ProductModel
import com.example.adidas.data.remote.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/**
 * This is an implementation of [ProductsRepository] to handle communication with [ApiInterface] server.
 * @author Malik Dawar
 */
class ProductsRepositoryImpl @Inject constructor(
    private val apiService: ApiInterface
) : ProductsRepository {

    @WorkerThread
    override suspend fun loadProducts():
            Flow<DataState<ArrayList<ProductModel>>> {
        return flow {
            apiService.loadProducts().apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(DataState.success(it))
                    }
                }
                // handle the case when the API request gets an error response.
                // e.g. internal server error.
            }.onErrorSuspend {
                emit(DataState.error<ArrayList<ProductModel>>(message()))
                // handle the case when the API request gets an exception response.
                // e.g. network connection error.
            }.onExceptionSuspend {
                if (this.exception is IOException) {
                    emit(DataState.error<ArrayList<ProductModel>>(noNetworkErrorMessage()))
                } else {
                    emit(DataState.error<ArrayList<ProductModel>>(somethingWentWrong()))
                }
            }
        }
    }
}