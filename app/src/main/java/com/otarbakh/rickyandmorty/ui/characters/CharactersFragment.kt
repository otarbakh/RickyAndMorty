package com.otarbakh.rickyandmorty.ui.characters

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.otarbakh.rickyandmorty.R
import com.otarbakh.rickyandmorty.common.BaseFragment
import com.otarbakh.rickyandmorty.databinding.FragmentCharactersBinding
import com.otarbakh.rickyandmorty.ui.adapters.CharactersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment :
    BaseFragment<FragmentCharactersBinding>(FragmentCharactersBinding::inflate) {

    private val charactersVm: CharactersViewModel by viewModels()
    private val charactersAdapter: CharactersAdapter by lazy { CharactersAdapter() }


    override fun viewCreated() {
        setupRecycler()
        lifecycleScope.launch {
            charactersVm.getCharacters()
            charactersVm.state.collectLatest {
                Log.d("FormulaWarmateba", it.toString())
                charactersAdapter.submitData(it)
            }
        }
    }
    private fun setupRecycler() {
        binding.rvBreakingNews.apply {
            adapter = charactersAdapter
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
    }

    override fun listeners() {

    }

}
