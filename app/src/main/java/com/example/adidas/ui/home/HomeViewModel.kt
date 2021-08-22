package com.example.adidas.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.example.adidas.data.DataState
import com.example.adidas.data.model.ProductModel
import com.example.adidas.data.usecases.FetchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * The HomeViewModel.kt, viewModel to perform the actions of the home screens and to manipulate the fetched data
 * @author Malik Dawar, malikdawar@hotmail.com
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchProductsUseCase: FetchProductsUseCase
) : ViewModel() {

    private val _query = MutableLiveData<String>()

    private var _uiState = MutableLiveData<HomeUiState>()
    var uiStateLiveData: LiveData<HomeUiState> = _uiState

    private var _productList = MutableLiveData<List<ProductModel>>()

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
                        _productList.postValue(dataState.data)
                    }

                    is DataState.Error -> {
                        _uiState.postValue(ErrorState(dataState.message))
                    }
                }
            }
        }
    }

    private val filteredData = Transformations.switchMap(_query) { filterable ->
        Transformations.map(_productList) { list ->
            if (filterable.isNotBlank()) {
                list.filter {
                    it.name?.toLowerCase(Locale.getDefault())?.contains(filterable)!! ||
                            it.description.toLowerCase(Locale.getDefault()).contains(filterable)
                }
            } else
                list
        }
    }

    val productListLiveData = MediatorLiveData<List<ProductModel>>().apply {
        addSource(_productList) { value -> this.setValue(value) }
        addSource(filteredData) { value -> this.setValue(value) }
    }

    fun onSearchContact(query: String) {
        if (query.length >= QUERY_THRESHOLD || query.isEmpty()) {
            _query.value = query.toLowerCase(Locale.getDefault()).trim()
        }
    }

    companion object {
        const val QUERY_THRESHOLD = 2
    }
}
