package com.otarbakh.rickyandmorty.ui.episodes

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.otarbakh.rickyandmorty.common.BaseFragment
import com.otarbakh.rickyandmorty.databinding.FragmentEpisodesBinding
import com.otarbakh.rickyandmorty.ui.adapters.EpisodesAdapter
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
    override fun listeners() {
        goToDetails()
        refreshEpisodes()
    }
    private fun refreshEpisodes(){
        binding.rvEpisodesSwipe.setOnRefreshListener {
            binding.rvEpisodesSwipe.isRefreshing = false
            getEpisodes()
        }
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
                    episode.id
                )
            )
        }
    }
}