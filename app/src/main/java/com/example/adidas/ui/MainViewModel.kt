package com.example.adidas.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.adidas.data.model.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
/**
 * The MainViewModel.kt, shared viewModel to transect the data b/w fragments
 * @author Malik Dawar, malikdawar@hotmail.com
 */


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var _photoModel = MutableLiveData<ProductModel>()
    var productModelLiveData: LiveData<ProductModel> = _photoModel

    fun savePhotoModel(product: ProductModel) {
        _photoModel.value = product
    }
}
