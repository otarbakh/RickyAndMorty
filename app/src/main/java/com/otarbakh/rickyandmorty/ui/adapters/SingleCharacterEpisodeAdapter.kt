package com.otarbakh.rickyandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otarbakh.rickyandmorty.data.model.characters.singlecharacter.SingleCharacterDto
import com.otarbakh.rickyandmorty.databinding.SingleCharacterLayoutBinding
import com.otarbakh.rickyandmorty.databinding.SingleEpisodesLayoutBinding


class SingleCharacterEpisodeAdapter :
    ListAdapter<String, SingleCharacterEpisodeAdapter.SingleCharacterEpisodesViewHolder>(
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

        fun bind(data: String) {

            binding.apply {
                tvSeason.text = data




//                binding.ivNewsImage.setOnClickListener {
//                    itemGotoLinkClickListener.invoke(data!!, absoluteAdapterPosition)
//                }

//                binding.btnShare.setOnClickListener {
//                    itemShareClickListener.invoke(data!!, absoluteAdapterPosition)
//                }
//
//                Glide.with(this.)
//                    .load(data?.image)
//                    .into(ivNewsImage)
            }

        }


    }

    fun setOnGotoClickListener(clickListener: (SingleCharacterDto, Int) -> Unit) {
        itemGotoLinkClickListener = clickListener
    }

    fun setOnShareClickListener(clickListener: (SingleCharacterDto, Int) -> Unit) {
        itemShareClickListener = clickListener
    }




    private class EpisodesDiffCallback : DiffUtil.ItemCallback<SingleCharacterDto>() {
        override fun areItemsTheSame(oldItem: SingleCharacterDto, newItem: SingleCharacterDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SingleCharacterDto, newItem: SingleCharacterDto): Boolean {
            return oldItem == newItem
        }
    }


}