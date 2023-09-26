package com.otarbakh.rickyandmorty.ui.locations

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.otarbakh.rickyandmorty.R
import com.otarbakh.rickyandmorty.common.BaseFragment
import com.otarbakh.rickyandmorty.databinding.FragmentCharactersBinding
import com.otarbakh.rickyandmorty.databinding.FragmentLocationsBinding
import com.otarbakh.rickyandmorty.ui.adapters.CharactersAdapter
import com.otarbakh.rickyandmorty.ui.characters.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//@AndroidEntryPoint
//class LocationsFragment :
//    BaseFragment<FragmentLocationsBinding>(FragmentLocationsBinding::inflate) {
//
//    private val locationsVm: CharactersViewModel by viewModels()
//    private val charactersAdapter: CharactersAdapter by lazy { CharactersAdapter() }
//
//
//    override fun viewCreated() {
//        setupRecycler()
//        lifecycleScope.launch {
//            locationsVm.getCharacters()
//            locationsVm.state.collectLatest {
//                Log.d("FormulaWarmateba", it.toString())
//                charactersAdapter.submitData(it)
//            }
//        }
//    }
//    private fun setupRecycler() {
//        binding.rvBreakingNews.apply {
//            adapter = charactersAdapter
//            layoutManager = GridLayoutManager(
//                requireContext(),
//                2,
//                GridLayoutManager.VERTICAL,
//                false
//            )
//        }
//    }
//
//    override fun listeners() {
//
//    }

//}

