package com.otarbakh.rickyandmorty.ui.singlecharacters

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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.otarbakh.rickyandmorty.R
import com.otarbakh.rickyandmorty.common.BaseFragment
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.databinding.FragmentSingleCharacterBinding
import com.otarbakh.rickyandmorty.ui.adapters.EpisodesAdapter
import com.otarbakh.rickyandmorty.ui.episodes.EpisodesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingleCharacterFragment :BaseFragment<FragmentSingleCharacterBinding>(FragmentSingleCharacterBinding::inflate) {

    private val singleCharacterVm: SingleCharacterViewModel by viewModels()
    private val args: SingleCharacterFragmentArgs by navArgs()
//    private val singleCharacterAdapter: SingleCharacterAdapter by lazy { SingleCharacterAdapter() }

    override fun viewCreated() {
        observe()
    }

    override fun listeners() {
    }
    private fun observe() {
//        setupRecycler()
        singleCharacterVm.getSingleCharacter(args.characterID)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                singleCharacterVm.state.collectLatest {
                    when (it) {
                        is Resource.Error -> {
                            Log.d("MISHA", it.error + " erori ")
                        }

                        is Resource.Loading -> {
                            Log.d("MISHA", it.loading.toString())
                        }

                        is Resource.Success -> {
                            delay(1000)
                            Log.d("MISHA", it.data.gender.toString())
                            binding.tvCharacterName.text = it.data.name
                            Glide.with(binding.ivCharacterPhoto)
                                .load(it.data?.image)
                                .into(binding.ivCharacterPhoto)

                        }
                    }
                }
            }
        }
    }

}