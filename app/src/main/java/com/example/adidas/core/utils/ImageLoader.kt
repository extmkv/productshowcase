package com.example.adidas.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.adidas.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

/**
 * A convenient function to load images from internet on ImageView.
 * @see Glide The library used which loads memory cached images.
 * @author Malik Dawar, malikdawar@hotmail.com
 */
private val shimmer =
    Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
        .setDuration(1800) // how long the shimmering animation takes to do one full sweep
        .setBaseAlpha(0.7f) //the alpha of the underlying children
        .setHighlightAlpha(0.6f) // the shimmer alpha amount
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

fun ImageView.load(url: String) {
    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
    Glide.with(this).load(url).placeholder(shimmerDrawable).into(this)
}