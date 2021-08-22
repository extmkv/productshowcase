package com.example.adidas.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel(
    @Expose val id: String,
    @Expose val currency: String,
    @Expose val price: Long,
    @Expose val name: String,
    @Expose val description: String,
    @Expose val imgUrl: String,
) : Parcelable