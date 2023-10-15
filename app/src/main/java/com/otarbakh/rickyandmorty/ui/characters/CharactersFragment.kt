package com.otarbakh.rickyandmorty.ui.characters

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.filter
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
        getAllCharacters()
        getSearchedCharacters()
        refreshCharacters()
        searchLogic()



//        if (isNetworkAvailable(requireContext())) {
//
//        } else {
//
//            Toast.makeText(requireContext(), "No internet connection available", Toast.LENGTH_SHORT).show()
//        }
    }

    override fun listeners() {
        goToDetails()

        refreshSearchedCharacters()
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

    fun goToDetails() {
        charactersAdapter.setOnGotoClickListener { charactersEntity, i ->
            findNavController().navigate(
                CharactersFragmentDirections.actionCharactersFragmentToSingleCharacterFragment(
                    charactersEntity.id!!
                )
            )
        }
    }

    fun refreshCharacters(){
        binding.layoutCharacters.setOnRefreshListener {
            binding.layoutCharacters.isRefreshing = false
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
        }
    }
    fun refreshSearchedCharacters(){
        binding.layoutRvSeachedCharacters.setOnRefreshListener {
            binding.layoutRvSeachedCharacters.isRefreshing = false
            getSearchedCharacters()
        }
    }

    fun getAllCharacters(){
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
    }
    fun getSearchedCharacters(){
        binding.layoutRvSeachedCharacters.isRefreshing = false
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                charactersVm.searchResults.collectLatest {result ->
                    when (result) {
                        is Resource.Loading -> {
                            loadingIndicator.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            loadingIndicator.visibility = View.INVISIBLE
                            searchedAdapter.submitList(result.data)
                        }
                        is Resource.Error -> {
                            loadingIndicator.visibility = View.INVISIBLE
                            val errorMessage = result.error
                        }
                    }

                }
            }
        }
    }

    fun searchLogic(){
        binding.apply {
            binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText.isNullOrEmpty() && newText.isNullOrBlank() && !isNetworkAvailable(requireContext())){
                        search()
                        binding.rvSearchCharacters.visibility = View.INVISIBLE
                        binding.layoutRvSeachedCharacters.visibility = View.INVISIBLE

                        binding.rvCharacters.visibility = View.VISIBLE
                        binding.layoutCharacters.visibility = View.VISIBLE

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
                    if (!newText.isNullOrEmpty() && !newText.isBlank() && isNetworkAvailable(requireContext())){
                        charactersVm.setSearchQuery(newText.orEmpty())
                        binding.rvSearchCharacters.visibility = View.VISIBLE
                        binding.layoutRvSeachedCharacters.visibility = View.VISIBLE

                        binding.rvCharacters.visibility = View.INVISIBLE
                        binding.layoutCharacters.visibility = View.INVISIBLE
                        getSearchedCharacters()


                    }
                    return true
                }
            })
            binding.etSearch.setOnCloseListener {
                binding.rvSearchCharacters.visibility = View.INVISIBLE
                binding.layoutRvSeachedCharacters.visibility = View.INVISIBLE
                binding.rvCharacters.visibility = View.VISIBLE
                binding.layoutCharacters.visibility = View.VISIBLE
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

    private fun searchCharacter(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                charactersVm.state.collectLatest {
                    val filteredContacts = it!!.filter {
                        it.name!!.contains(query, true)
                    }
                    charactersAdapter.submitData(filteredContacts)
                }
            }
        }
    }
    private fun search() {
        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchCharacter(newText)
                return true
            }
        })
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}