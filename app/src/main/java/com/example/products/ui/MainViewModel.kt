package com.example.products.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.products.data.model.PhotoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
/**
 * The MainViewModel.kt, shared viewModel to transect the data b/w fragments
 * @author Malik Dawar, malikdawar@hotmail.com
 */


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var _photoModel = MutableLiveData<PhotoModel>()
    var photoModelLiveData: LiveData<PhotoModel> = _photoModel

    fun savePhotoModel(photo: PhotoModel) {
        _photoModel.value = photo
    }
}
