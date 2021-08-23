package com.example.adidas.data

import com.example.adidas.data.model.ProductModel

object ProductDataSource {

    fun getProductList(): ArrayList<ProductModel> {
        val productList = ArrayList<ProductModel>()
        productList.add(productModel)
        return productList
    }

    private val productModel: ProductModel
        get() = ProductModel(
            id = "HI333",
            currency = "$",
            price = 44,
            name = "Lorem ipsum",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            imgUrl = "https://assets.adidas.com/images/w_320,h_320,f_auto,q_auto:sensitive,fl_lossy/c7099422ccc14e44b406abec00ba6c96_9366/NMD_R1_V2_Shoes_Black_FY6862_01_standard.jpg"
        )

}