package com.otarbakh.rickyandmorty.ui.locations

import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
    private lateinit var loadingIndicator: ProgressBar
    override fun viewCreated() {
        setupRecycler()
        getLocations()
    }

    override fun listeners() {
        goToDetails()
        refreshLocations()
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

    private fun refreshLocations(){
        binding.rvLocationsSwipe.setOnRefreshListener {
            binding.rvLocationsSwipe.isRefreshing = false
            getLocations()
        }
    }


    private fun getLocations() {
        setupRecycler()
        lifecycleScope.launch {
            locationsVm.getLocations()
            locationsVm.state.collectLatest {
                locationAdapter.submitData(it)
            }
        }
    }

    private fun goToDetails() {
        locationAdapter.setOnGotoClickListener { locations, i ->
            findNavController().navigate(
                LocationsFragmentDirections.actionLocationsFragmentToSingleLocationFragment(
                    locations.id!!
                )
            )
        }
    }
}

