package com.otarbakh.rickyandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.databinding.SingleEpisodesLayoutBinding

class EpisodesAdapter :
    PagingDataAdapter<EpisodesEntity, EpisodesAdapter.EpisodesViewHolder>(
        EpisodesDiffCallback()
    ) {

    private lateinit var itemGotoLinkClickListener: (EpisodesEntity, Int) -> Unit
    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {

        val data = getItem(position)

        holder.bind(data)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        return EpisodesViewHolder(
            SingleEpisodesLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class EpisodesViewHolder(
        private val binding: SingleEpisodesLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: EpisodesEntity?) {

            binding.apply {
                tvSeason.text = data?.episode
                tvEpisodeName.text = data?.name
                tvReleaseDate.text = data?.air_date

                binding.ivGotoNext.setOnClickListener {
                    itemGotoLinkClickListener.invoke(data!!, absoluteAdapterPosition)
                }
            }
        }
    }

    fun setOnGotoClickListener(clickListener: (EpisodesEntity, Int) -> Unit) {
        itemGotoLinkClickListener = clickListener
    }

    private class EpisodesDiffCallback : DiffUtil.ItemCallback<EpisodesEntity>() {
        override fun areItemsTheSame(oldItem: EpisodesEntity, newItem: EpisodesEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EpisodesEntity, newItem: EpisodesEntity): Boolean {
            return oldItem == newItem
        }
    }


}