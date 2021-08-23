package com.example.adidas.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewModel(
    @Expose val productId: String? = null,
    @Expose val rating: Int,
    @Expose val text: String
) : Parcelable