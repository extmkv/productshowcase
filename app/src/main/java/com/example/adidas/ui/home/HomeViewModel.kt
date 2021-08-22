package com.example.adidas.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adidas.data.DataState
import com.example.adidas.data.model.ProductModel
import com.example.adidas.data.usecases.FetchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The HomeViewModel.kt, viewModel to perform the actions of the home screens and to manipulate the fetched data
 * @author Malik Dawar, malikdawar@hotmail.com
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchProductsUseCase: FetchProductsUseCase
) : ViewModel() {

    private var _uiState = MutableLiveData<HomeUiState>()
    var uiStateLiveData: LiveData<HomeUiState> = _uiState

    private var _photosList = MutableLiveData<List<ProductModel>>()
    var photosListLiveData: LiveData<List<ProductModel>> = _photosList

    init {
        fetchProducts()
    }

    @SuppressLint("NullSafeMutableLiveData")
    private fun fetchProducts() {
        _uiState.postValue(LoadingState)

        viewModelScope.launch {
            fetchProductsUseCase.invoke().collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        // First page
                        _uiState.postValue(ContentState)
                        _photosList.postValue(dataState.data)
                    }

                    is DataState.Error -> {
                        _uiState.postValue(ErrorState(dataState.message))
                    }
                }
            }
        }
    }
}
