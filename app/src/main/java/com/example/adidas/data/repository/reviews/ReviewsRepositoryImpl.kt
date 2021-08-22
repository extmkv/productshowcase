package com.example.adidas.data.repository.reviews

import androidx.annotation.WorkerThread
import com.example.adidas.core.extensions.noNetworkErrorMessage
import com.example.adidas.core.extensions.somethingWentWrong
import com.example.adidas.data.DataState
import com.example.adidas.data.model.ReviewModel
import com.example.adidas.data.remote.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/**
 * This is an implementation of [ReviewsRepository] to handle communication with [ProductApiInterface] server.
 * @author Malik Dawar
 */
class ReviewsRepositoryImpl @Inject constructor(
    private val reviewApiInterface: ReviewApiInterface
) : ReviewsRepository {

    @WorkerThread
    override suspend fun fetchReviews(productId: String):
            Flow<DataState<ArrayList<ReviewModel>>> {
        return flow {
            reviewApiInterface.fetchProductReviews(productId).apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(DataState.success(it))
                    }
                }
                // handle the case when the API request gets an error response.
                // e.g. internal server error.
            }.onErrorSuspend {
                emit(DataState.error<ArrayList<ReviewModel>>(message()))
                // handle the case when the API request gets an exception response.
                // e.g. network connection error.
            }.onExceptionSuspend {
                if (this.exception is IOException) {
                    emit(DataState.error<ArrayList<ReviewModel>>(noNetworkErrorMessage()))
                } else {
                    emit(DataState.error<ArrayList<ReviewModel>>(somethingWentWrong()))
                }
            }
        }
    }
}