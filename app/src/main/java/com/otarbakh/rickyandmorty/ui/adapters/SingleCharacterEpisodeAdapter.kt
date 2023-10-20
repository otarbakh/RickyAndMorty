package com.otarbakh.rickyandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otarbakh.rickyandmorty.data.model.characters.singlecharacter.SingleCharacterDto
import com.otarbakh.rickyandmorty.data.model.episodes.multipleepisode.MultipleEpisodesDtoItem
import com.otarbakh.rickyandmorty.databinding.SingleCharacterLayoutBinding
import com.otarbakh.rickyandmorty.databinding.SingleEpisodesLayoutBinding


class SingleCharacterEpisodeAdapter :
    ListAdapter<MultipleEpisodesDtoItem, SingleCharacterEpisodeAdapter.SingleCharacterEpisodesViewHolder>(
        EpisodesDiffCallback()
    ) {


    private lateinit var itemGotoLinkClickListener: (SingleCharacterDto, Int) -> Unit
    private lateinit var itemShareClickListener: (SingleCharacterDto, Int) -> Unit

    override fun onBindViewHolder(holder: SingleCharacterEpisodesViewHolder, position: Int) {

        val data = getItem(position)

        holder.bind(data)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleCharacterEpisodesViewHolder {
        return SingleCharacterEpisodesViewHolder(
            SingleEpisodesLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    inner class SingleCharacterEpisodesViewHolder(
        private val binding: SingleEpisodesLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MultipleEpisodesDtoItem) {

            binding.apply {
                tvSeason.text = data.episode
                tvEpisodeName.text = data.name
                tvReleaseDate.text = data.air_date
            }
        }
    }


    private class EpisodesDiffCallback : DiffUtil.ItemCallback<MultipleEpisodesDtoItem>() {
        override fun areItemsTheSame(oldItem: MultipleEpisodesDtoItem, newItem: MultipleEpisodesDtoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MultipleEpisodesDtoItem, newItem: MultipleEpisodesDtoItem): Boolean {
            return oldItem == newItem
        }
    }


}