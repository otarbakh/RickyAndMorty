package com.otarbakh.rickyandmorty.ui.single.singlelocations

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.otarbakh.rickyandmorty.common.BaseFragment
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.databinding.FragmentSingleLocationBinding
import com.otarbakh.rickyandmorty.ui.adapters.SingleLocationCharactersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingleLocationFragment :
    BaseFragment<FragmentSingleLocationBinding>(FragmentSingleLocationBinding::inflate) {

    private val vm: SingleLocationViewModel by viewModels()
    private val args: SingleLocationFragmentArgs by navArgs()
    private var characterUrls: List<String> = emptyList()
    private val singleCharacterAdapter: SingleLocationCharactersAdapter by lazy { SingleLocationCharactersAdapter() }

    override fun viewCreated() {
        observe()
    }

    override fun listeners() {
    }

    private fun observe() {
        setupRecycler()
        vm.getSingleEpisode(args.LocationId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collectLatest {
                    when (it) {
                        is Resource.Error -> {

                        }

                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                         binding.tvLocationName.text = it.data.name
                         binding.tvLocationType.text = it.data.type
                            characterUrls = it.data.residents
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
        binding.rvLocationsCharacters.apply {
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




