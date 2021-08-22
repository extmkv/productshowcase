package com.example.products.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotosResponse(
    @field:SerializedName("hits")
    val hits: List<PhotoModel>? = null,
) : Parcelable

@Parcelize
data class PhotoModel(
    @Expose val id: Int,
    @Expose val type: String,
    @Expose val previewURL: String,
    @Expose val largeImageURL: String,
    @Expose val downloads: Int,
    @Expose val views: Int,
    @Expose val user: String,
    @Expose val userImageURL: String,
) : Parcelable