package com.example.adidas.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adidas.data.DataState
import com.example.adidas.data.model.ProductModel
import com.example.adidas.data.model.ReviewModel
import com.example.adidas.data.usecases.FetchReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val fetchReviewsUseCase: FetchReviewsUseCase
) : ViewModel() {

    private var _productModel = MutableLiveData<ProductModel>()
    var productModelLiveData: LiveData<ProductModel> = _productModel

    fun initPhotoModelFromSharedViewModel(product: ProductModel) {
        _productModel.value = product
    }

    private var _uiState = MutableLiveData<DetailsUiState>()
    var uiStateLiveData: LiveData<DetailsUiState> = _uiState

    private var _reviewList = MutableLiveData<List<ReviewModel>>()
    var productReviewListLiveData: LiveData<List<ReviewModel>> = _reviewList

    fun fetchProductReviews(productId: String) {
        _uiState.postValue(LoadingState)

        viewModelScope.launch {
            fetchReviewsUseCase.invoke(productId).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        // First page
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
}
