package com.example.adidas.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.adidas.adapters.RatingItemAdapter
import com.example.adidas.base.BaseFragment
import com.example.adidas.core.extensions.showToastMsg
import com.example.adidas.core.utils.load
import com.example.adidas.databinding.FragmentPhotoDetailsBinding
import com.example.adidas.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * The DetailsFragment.kt
 * @author Malik Dawar, malikdawar@hotmail.com
 */

@AndroidEntryPoint
class DetailsFragment : BaseFragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentPhotoDetailsBinding
    private lateinit var ratingItemAdapter: RatingItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel.productModelLiveData.value?.let {
            viewModel.initPhotoModelFromSharedViewModel(it)
            viewModel.fetchProductReviews()
        }

        ratingItemAdapter = RatingItemAdapter().also {
            it.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            binding.productReviewsRecyclerView.adapter = it
        }

        initObservations()
    }

    private fun initObservations() {
        viewModel.productModelLiveData.observe(viewLifecycleOwner) {
            binding.imgDetailsProductPhoto.load(it.imgUrl)
        }

        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState -> {
                    progressDialog.show()
                }
                is ContentState -> {
                    progressDialog.dismiss()
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
}
