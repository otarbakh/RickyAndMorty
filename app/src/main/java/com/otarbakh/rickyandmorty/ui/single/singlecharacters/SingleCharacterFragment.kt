package com.otarbakh.rickyandmorty.ui.single.singlecharacters

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.otarbakh.rickyandmorty.common.BaseFragment
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.databinding.FragmentSingleCharacterBinding
import com.otarbakh.rickyandmorty.ui.adapters.SingleCharacterEpisodeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingleCharacterFragment :
    BaseFragment<FragmentSingleCharacterBinding>(FragmentSingleCharacterBinding::inflate) {

    private val singleCharacterVm: SingleCharacterViewModel by viewModels()
    private val args: SingleCharacterFragmentArgs by navArgs()
    private var characterUrls: List<String> = emptyList()
    private val singleCharacterAdapter: SingleCharacterEpisodeAdapter by lazy { SingleCharacterEpisodeAdapter() }

    override fun viewCreated() {
        observe()
    }

    override fun listeners() {
    }

    private fun observe() {
        setupRecycler()
        singleCharacterVm.getSingleCharacter(args.characterID)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                singleCharacterVm.state.collectLatest {
                    when (it) {
                        is Resource.Error -> {}

                        is Resource.Loading -> {}

                        is Resource.Success -> {
                         binding.tvCharacterName.text = it.data.name
                            Glide.with(binding.ivCharacterPhoto)
                                .load(it.data?.image)
                                .into(binding.ivCharacterPhoto)
                            characterUrls = it.data.episode
                            singleCharacterVm.getMultipleEpisodes(extractIdsFromUrls())
                            singleCharacterVm.episodesState.collectLatest { episodes ->
                                when (episodes) {
                                    is Resource.Error -> {}

                                    is Resource.Loading -> {}

                                    is Resource.Success -> {
                                        singleCharacterAdapter.submitList(episodes.data)
                                    }
                                }
                            }
                        }

                    }
                }
            }
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




