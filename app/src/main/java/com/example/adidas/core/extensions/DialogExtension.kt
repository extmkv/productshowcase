package com.example.adidas.core.extensions

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.widget.*
import com.example.adidas.R
import com.example.adidas.data.model.ReviewModel

/**
 * Extension function to showCustomChatMenuDialogue
 * @author Dawar Malik.
 */
fun Context.showCustomChatMenuDialogue(
    onClick: (ReviewModel) -> Unit
) {
    val dialog = Dialog(this)
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.ui_custom_rating_dialogue)

    val ratingBarRatingView = dialog.findViewById(R.id.ratingBarRatingView) as RatingBar
    val etRatingView = dialog.findViewById(R.id.etRatingView) as EditText
    val btnRatingView = dialog.findViewById(R.id.btnRatingView) as Button

    btnRatingView.setOnClickListener {
        onClick.invoke(
            ReviewModel(
                rating = ratingBarRatingView.rating.toInt(),
                text = etRatingView.text.toString()
            )
        )
        dialog.dismiss()
    }

    dialog.window?.setGravity(Gravity.BOTTOM)
    val window: Window? = dialog.window
    window?.setLayout(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    dialog.show()
}
