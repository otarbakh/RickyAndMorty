package com.otarbakh.rickyandmorty.ui.episodes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.filter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.otarbakh.rickyandmorty.R
import com.otarbakh.rickyandmorty.common.BaseFragment
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.databinding.FragmentEpisodesBinding
import com.otarbakh.rickyandmorty.ui.adapters.CharactersAdapter
import com.otarbakh.rickyandmorty.ui.adapters.EpisodesAdapter
import com.otarbakh.rickyandmorty.ui.characters.CharactersViewModel
import com.otarbakh.rickyandmorty.ui.locations.LocationsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>(FragmentEpisodesBinding::inflate) {

    private val episodesVm: EpisodesViewModel by viewModels()
    private val episodeAdapter: EpisodesAdapter by lazy { EpisodesAdapter() }

    override fun viewCreated() {
        getEpisodes()
    }

    private fun setupRecycler() {
        binding.rvEpisodes.apply {
            adapter = episodeAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun listeners() {
        goToDetails()
    }

    private fun getEpisodes() {
        setupRecycler()
        lifecycleScope.launch {
            episodesVm.getEpisodes()
            episodesVm.state.collectLatest {
                episodeAdapter.submitData(it)
            }
        }

    }

    private fun goToDetails() {
        episodeAdapter.setOnGotoClickListener { episode, i ->
            findNavController().navigate(
                EpisodesFragmentDirections.actionEpisodesFragmentToSingleEpisodeFragment(
                    episode.id!!
                )
            )
        }
    }
}