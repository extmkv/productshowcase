package com.example.adidas.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    private lateinit var binding: FragmentHomeBinding
    private lateinit var productItemAdapter: ProductItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productItemAdapter = ProductItemAdapter().also {
            it.onPhotoSelectionListener { photoModel ->
                mainActivity.saveProductItem(photoModel)
                replaceFragment(DetailsFragment(), addToBackStack = true)
            }

            it.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            binding.productRecyclerView.adapter = it
        }

        binding.editTextSearchView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.onSearchContact(s.toString())
            }
        })

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

        viewModel.productListLiveData.observe(viewLifecycleOwner) { photos ->
            productItemAdapter.setItems(photos)
        }
    }
}
