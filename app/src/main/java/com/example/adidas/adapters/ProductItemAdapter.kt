package com.example.adidas.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adidas.core.utils.load
import com.example.adidas.data.model.ProductModel
import com.example.adidas.databinding.RowItemProductBinding

class ProductItemAdapter :
    RecyclerView.Adapter<ProductItemAdapter.ProductViewHolder>() {

    lateinit var onPhotoSelected: (ProductModel) -> Unit
    private val productItems: ArrayList<ProductModel> = arrayListOf()

    fun setItems(products: List<ProductModel>) {
        productItems.clear()
        productItems.addAll(products)
        notifyDataSetChanged()
    }

    fun onPhotoSelectionListener(listener: (ProductModel) -> Unit) {
        onPhotoSelected = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RowItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productItems[position])
    }

    override fun getItemCount() = productItems.size

    inner class ProductViewHolder(private val rowItemPhotoBinding: RowItemProductBinding) :
        RecyclerView.ViewHolder(rowItemPhotoBinding.root) {

        fun bind(productModel: ProductModel) {
            rowItemPhotoBinding.apply {
                imageItemProductLogo.load(productModel.imgUrl)
                tvItemProductTitle.text = productModel.name
                tvItemProductDetails.text = productModel.description
                @SuppressLint("SetTextI18n")
                tvItemProductPrice.text = "${productModel.price} ${productModel.currency}"

                containerItemProduct.setOnClickListener {
                    onPhotoSelected(productModel)
                }
            }
        }
    }
}
