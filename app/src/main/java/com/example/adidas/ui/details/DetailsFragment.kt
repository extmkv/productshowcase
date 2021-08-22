package com.example.adidas.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.adidas.base.BaseFragment
import com.example.adidas.core.extensions.backPress
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
    private lateinit var viewBinding: FragmentPhotoDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainViewModel.productModelLiveData.value?.let {
            viewModel.initPhotoModelFromSharedViewModel(it)
            viewModel.fetchProductReviews(it.id)
        }

        initObservations()
    }

    private fun initObservations() {
        viewModel.productModelLiveData.observe(viewLifecycleOwner) {
            viewBinding.photoView.load(it.imgUrl)
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

        }
    }
}
