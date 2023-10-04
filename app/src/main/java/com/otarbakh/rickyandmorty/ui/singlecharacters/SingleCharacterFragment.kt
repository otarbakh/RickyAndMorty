package com.otarbakh.rickyandmorty.ui.singlecharacters

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.otarbakh.rickyandmorty.R
import com.otarbakh.rickyandmorty.common.BaseFragment
import com.otarbakh.rickyandmorty.databinding.FragmentSingleCharacterBinding
import com.otarbakh.rickyandmorty.ui.adapters.EpisodesAdapter
import com.otarbakh.rickyandmorty.ui.episodes.EpisodesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleCharacterFragment :BaseFragment<FragmentSingleCharacterBinding>(FragmentSingleCharacterBinding::inflate) {

    private val singleCharacterVm: SingleCharacterViewModel by viewModels()
    private val singleCharacterAdapter: SingleCharacterAdapter by lazy { SingleCharacterAdapter() }

    override fun viewCreated() {

    }

    override fun listeners() {
    }


}