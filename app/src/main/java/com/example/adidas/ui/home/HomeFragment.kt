package com.example.adidas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.adidas.adapters.ProductItemAdapter
import com.example.adidas.base.BaseFragment
import com.example.adidas.core.extensions.replaceFragment
import com.example.adidas.core.extensions.showToastMsg
import com.example.adidas.databinding.FragmentHomeBinding
import com.example.adidas.ui.details.DetailsFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * The HomeFragment.kt
 * @author Malik Dawar, malikdawar@hotmail.com
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var productItemAdapter: ProductItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productItemAdapter = ProductItemAdapter().also {
            it.onPhotoSelectionListener { photoModel ->
                mainActivity.saveProductItem(photoModel)
                replaceFragment(DetailsFragment(), addToBackStack = true)
            }

            it.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            viewBinding.photosRecyclerView.adapter = it
        }

        initObservations()
    }

    private fun initObservations() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState -> {
                    progressDialog.show()
                }

                is ContentState, ContentNextPageState -> {
                    progressDialog.dismiss()
                }

                is ErrorState -> {
                    progressDialog.dismiss()
                    showToastMsg(state.message)
                }
            }
        }

        viewModel.photosListLiveData.observe(viewLifecycleOwner) { photos ->
            productItemAdapter.setItems(photos)
        }
    }
}
