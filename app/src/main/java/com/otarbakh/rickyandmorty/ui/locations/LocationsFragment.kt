package com.otarbakh.rickyandmorty.ui.locations

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.otarbakh.rickyandmorty.common.BaseFragment
import com.otarbakh.rickyandmorty.databinding.FragmentLocationsBinding
import com.otarbakh.rickyandmorty.ui.adapters.LocationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationsFragment :
    BaseFragment<FragmentLocationsBinding>(FragmentLocationsBinding::inflate) {

    private val locationsVm: LocationsViewModel by viewModels()
    private val locationAdapter: LocationAdapter by lazy { LocationAdapter() }


    override fun viewCreated() {
        setupRecycler()
        lifecycleScope.launch {
            locationsVm.getLocations()
            locationsVm.state.collectLatest {
                Log.d("FormulaWarmateba", it.toString())
                locationAdapter.submitData(it)
            }
        }
    }
    private fun setupRecycler() {
        binding.rvLocations.apply {
            adapter = locationAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun listeners() {

    }

}

