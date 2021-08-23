package com.example.adidas.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.example.adidas.R
import com.example.adidas.base.BaseActivity
import com.example.adidas.core.extensions.replaceFragmentSafely
import com.example.adidas.data.model.ProductModel
import com.example.adidas.ui.home.HomeFragment
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

    fun saveProductItem(product: ProductModel) {
        viewModel.saveProductModel(product)
    }
}