package com.example.adidas.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adidas.data.DataState
import com.example.adidas.data.model.ProductModel
import com.example.adidas.data.model.ReviewModel
import com.example.adidas.data.usecases.FetchReviewsUseCase
import com.example.adidas.data.usecases.SubmitReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val fetchReviewsUseCase: FetchReviewsUseCase,
    private val submitReviewUseCase: SubmitReviewUseCase,
) : ViewModel() {

    var productId: String = ""
    private var _productModel = MutableLiveData<ProductModel>()
    var productModelLiveData: LiveData<ProductModel> = _productModel

    fun initPhotoModelFromSharedViewModel(product: ProductModel) {
        _productModel.value = product
        productId= product.id
    }

    private var _uiState = MutableLiveData<DetailsUiState>()
    var uiStateLiveData: LiveData<DetailsUiState> = _uiState

    private var _reviewList = MutableLiveData<List<ReviewModel>>()
    var productReviewListLiveData: LiveData<List<ReviewModel>> = _reviewList

    fun fetchProductReviews() {
        _uiState.postValue(LoadingState)

        viewModelScope.launch {
            fetchReviewsUseCase.invoke(productId).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        _uiState.postValue(ContentState)
                        _reviewList.postValue(dataState.data!!)
                    }

                    is DataState.Error -> {
                        _uiState.postValue(ErrorState(dataState.message))
                    }
                }
            }
        }
    }

    fun submitProductReviews(review: ReviewModel) {
        _uiState.postValue(LoadingState)

        viewModelScope.launch {
            submitReviewUseCase.invoke(productId, review).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> { //dataState.data, use it to update the UI if required!
                        _uiState.postValue(SubmissionState("Review submitted successfully"))
                    }

                    is DataState.Error -> {
                        _uiState.postValue(ErrorState(dataState.message))
                    }
                }
            }
        }
    }
}
