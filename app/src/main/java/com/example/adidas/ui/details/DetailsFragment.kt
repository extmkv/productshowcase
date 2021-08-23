package com.example.adidas.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.adidas.R
import com.example.adidas.adapters.RatingItemAdapter
import com.example.adidas.base.BaseFragment
import com.example.adidas.core.extensions.backPress
import com.example.adidas.core.extensions.showCustomChatMenuDialogue
import com.example.adidas.core.extensions.showToastMsg
import com.example.adidas.core.utils.load
import com.example.adidas.databinding.FragmentProductDetailsBinding
import com.example.adidas.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * The DetailsFragment.kt
 * @author Malik Dawar, malikdawar@hotmail.com
 */

@AndroidEntryPoint
class DetailsFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: DetailsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var ratingItemAdapter: RatingItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel.productModelLiveData.value?.let {
            viewModel.initProductModelFromSharedViewModel(it)
        }
        viewModel.fetchProductReviews()

        ratingItemAdapter = RatingItemAdapter().also {
            it.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            binding.recyclerViewProductReviews.adapter = it
        }

        initObservations()

        with(binding) {
            imgDetailsProductBack.setOnClickListener(this@DetailsFragment)
            btnViewProductReview.setOnClickListener(this@DetailsFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initObservations() {
        viewModel.productModelLiveData.observe(viewLifecycleOwner) {
            with(binding) {
                imgDetailsProductPhoto.load(it.imgUrl)
                tvDetailsProductName.text = it.name
                tvDetailsProductPrice.text = it.price.toString() + it.currency
                tvDetailsProductDetails.text = it.description
            }
        }

        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState -> {
                    progressDialog.show()
                }
                is ContentState -> {
                    progressDialog.dismiss()
                }
                is SubmissionState -> {
                    showToastMsg(state.message)
                    progressDialog.dismiss()
                    viewModel.fetchProductReviews()
                }
                is ErrorState -> {
                    progressDialog.dismiss()
                    showToastMsg(state.message)
                }
            }
        }

        viewModel.productReviewListLiveData.observe(viewLifecycleOwner) { reviews ->
            ratingItemAdapter.setItems(reviews)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgDetailsProductBack -> mainActivity.backPress()
            R.id.btnViewProductReview -> mainActivity.showCustomChatMenuDialogue {
                viewModel.submitProductReviews(it)
            }
        }
    }
}
