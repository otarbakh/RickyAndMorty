package com.otarbakh.rickyandmorty.ui.single.singleEpisodes

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.otarbakh.rickyandmorty.R
import com.otarbakh.rickyandmorty.common.BaseFragment
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.databinding.FragmentSingleEpisodeBinding
import com.otarbakh.rickyandmorty.ui.adapters.SingleEpisodeCharactersAdapter
import com.otarbakh.rickyandmorty.ui.single.singlecharacters.SingleEpisodeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingleEpisodeFragment :
    BaseFragment<FragmentSingleEpisodeBinding>(FragmentSingleEpisodeBinding::inflate) {

    private val vm: SingleEpisodeViewModel by viewModels()
    private val args: SingleEpisodeFragmentArgs by navArgs()
    private var characterUrls: List<String> = emptyList()
    private val singleCharacterAdapter: SingleEpisodeCharactersAdapter by lazy { SingleEpisodeCharactersAdapter() }

    override fun viewCreated() {
        observe()
    }

    override fun listeners() {
        goBack()
    }

    private fun observe() {
        setupRecycler()
        vm.getSingleEpisode(args.episodeId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collectLatest {
                    when (it) {
                        is Resource.Error -> {}

                        is Resource.Loading -> {}

                        is Resource.Success -> {
                            binding.tvEpisodesImpl.text = it.data.name
                            binding.tvReleaseDate.text = it.data.air_date
                            binding.tvEpisodeName.text = it.data.name
                            binding.tvSeason.text = it.data.episode
                            characterUrls = it.data.characters
                            vm.getMultipleCharacters(extractIdsFromUrls())
                            vm.characterState.collectLatest { chars ->
                                when (chars) {
                                    is Resource.Error -> {}

                                    is Resource.Loading -> {}

                                    is Resource.Success -> {
                                        singleCharacterAdapter.submitList(chars.data)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun goBack(){
        binding.ivArrowBack.setOnClickListener{
            findNavController().navigate(R.id.action_singleEpisodeFragment_to_episodesFragment)
        }
    }
    fun extractIdsFromUrls(): List<Int> {
        return characterUrls.mapNotNull { extractIdFromUrl(it) }
    }

    private fun extractIdFromUrl(url: String): Int? {
        val parts = url.split("/")
        if (parts.isNotEmpty()) {
            val idString = parts.last()
            return idString.toIntOrNull()
        }
        return null
    }

    private fun setupRecycler() {
        binding.rvCharactersEpisodes.apply {
            adapter = singleCharacterAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            )
        }
    }
}




