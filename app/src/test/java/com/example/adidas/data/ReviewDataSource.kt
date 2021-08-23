package com.example.adidas.data

import com.example.adidas.data.model.ReviewModel

object ReviewDataSource {

    val reviewModel: ReviewModel
        get() = ReviewModel(
            productId = "HI333",
            rating = 5,
            text = "hello this is test"
        )
}