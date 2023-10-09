package com.otarbakh.rickyandmorty.ui.characters

import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.otarbakh.rickyandmorty.common.BaseFragment
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.databinding.FragmentCharactersBinding
import com.otarbakh.rickyandmorty.ui.adapters.CharactersAdapter
import com.otarbakh.rickyandmorty.ui.adapters.SearchedCharactersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment :
    BaseFragment<FragmentCharactersBinding>(FragmentCharactersBinding::inflate) {

    private val charactersVm: CharactersViewModel by viewModels()
    private val charactersAdapter: CharactersAdapter by lazy { CharactersAdapter() }
    private val searchedAdapter: SearchedCharactersAdapter by lazy { SearchedCharactersAdapter() }
    private lateinit var loadingIndicator: ProgressBar


    override fun viewCreated() {
        setupRecycler()
        setupSearchRecycler()
        loadingIndicator = binding.loadingIndicator

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                charactersVm.getCharacters()
                charactersVm.state.collectLatest {
                    if (it != null) {
                        charactersAdapter.submitData(it)
                    }
                }
            }
        }




        binding.apply {
            binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    charactersVm.setSearchQuery(query.orEmpty())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText.isNullOrEmpty() && newText.isNullOrBlank()){
                        binding.rvSearchCharacters.visibility = View.INVISIBLE
                        binding.rvCharacters.visibility = View.VISIBLE
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                charactersVm.state.collectLatest {
                                    if (it != null) {
                                        charactersAdapter.submitData(it)
                                    }
                                }
                            }
                        }
                    }
                    if(!newText.isNullOrEmpty()){
                        binding.rvSearchCharacters.visibility = View.VISIBLE
                        binding.rvCharacters.visibility = View.INVISIBLE
                        observe()
                    }
                    charactersVm.setSearchQuery(newText.orEmpty())
                    return true
                }
            })

            binding.etSearch.setOnCloseListener {
                binding.rvSearchCharacters.visibility = View.INVISIBLE
                binding.rvCharacters.visibility = View.VISIBLE
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        charactersVm.state.collectLatest {
                            if (it != null) {
                                charactersAdapter.submitData(it)
                            }
                        }
                    }
                }
                false
            }
        }
    }

    private fun setupRecycler() {
        binding.rvCharacters.apply {
            adapter = charactersAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun setupSearchRecycler() {
        binding.rvSearchCharacters.apply {
            adapter = searchedAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun listeners() {
        goToDetails()
    }

    fun goToDetails() {
        charactersAdapter.setOnGotoClickListener { charactersEntity, i ->
            findNavController().navigate(
                CharactersFragmentDirections.actionCharactersFragmentToSingleCharacterFragment(
                    charactersEntity.id!!
                )
            )
        }
    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launch {
            charactersVm.searchResults.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        loadingIndicator.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        loadingIndicator.visibility = View.INVISIBLE
                        searchedAdapter.submitList(resource.data)
                    }
                    is Resource.Error -> {
                        loadingIndicator.visibility = View.INVISIBLE
                        val errorMessage = resource.error
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}