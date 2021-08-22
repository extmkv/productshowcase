package com.example.products.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.example.products.R
import com.example.products.base.BaseActivity
import com.example.products.core.extensions.replaceFragmentSafely
import com.example.products.data.model.PhotoModel
import com.example.products.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * The MainActivity.kt, Main activity class, launcher activity
 * @author Malik Dawar, malikdawar@hotmail.com
 */

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragmentSafely(HomeFragment())
    }

    fun savePhotoModel(photo: PhotoModel) {
        viewModel.savePhotoModel(photo)
    }
}