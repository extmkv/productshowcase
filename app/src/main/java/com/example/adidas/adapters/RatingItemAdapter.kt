package com.example.adidas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adidas.data.model.ReviewModel
import com.example.adidas.databinding.RowItemRatingBinding

class RatingItemAdapter :
    RecyclerView.Adapter<RatingItemAdapter.ReviewsViewHolder>() {

    private val ratingItems: ArrayList<ReviewModel> = arrayListOf()

    fun setItems(products: List<ReviewModel>) {
        ratingItems.clear()
        ratingItems.addAll(products)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val binding = RowItemRatingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        holder.bind(ratingItems[position])
    }

    override fun getItemCount() = ratingItems.size

    inner class ReviewsViewHolder(private val rowItemPhotoBinding: RowItemRatingBinding) :
        RecyclerView.ViewHolder(rowItemPhotoBinding.root) {

        fun bind(rating: ReviewModel) {
            rowItemPhotoBinding.apply {
                ratingBarRatingView.rating = rating.rating.toFloat()
                tvItemProductReview.text = rating.text
            }
        }
    }
}
