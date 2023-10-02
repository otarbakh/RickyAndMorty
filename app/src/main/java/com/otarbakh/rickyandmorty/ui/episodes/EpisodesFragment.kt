package com.otarbakh.rickyandmorty.ui.episodes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.otarbakh.rickyandmorty.R
import com.otarbakh.rickyandmorty.common.BaseFragment
import com.otarbakh.rickyandmorty.databinding.FragmentEpisodesBinding
import com.otarbakh.rickyandmorty.ui.adapters.CharactersAdapter
import com.otarbakh.rickyandmorty.ui.characters.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>(FragmentEpisodesBinding::inflate) {

    private val episodesVm: EpisodesViewModel by viewModels()
    private val episodeAdapter:  by lazy { CharactersAdapter() }

    override fun viewCreated() {

    }

    override fun listeners() {

    }


}