package com.otarbakh.rickyandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity

import com.otarbakh.rickyandmorty.databinding.SingleCharacterLayoutBinding

class EpisodesAdapter :
    PagingDataAdapter<EpisodesEntity, EpisodesAdapter.EpisodesViewHolder>(
        EpisodesDiffCallback()
    ) {


    private lateinit var itemGotoLinkClickListener: (EpisodesEntity, Int) -> Unit
    private lateinit var itemShareClickListener: (EpisodesEntity, Int) -> Unit

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {

        val data = getItem(position)

        holder.bind(data)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {

        return EpisodesViewHolder(
            SingleCharacterLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    inner class EpisodesViewHolder(
        private val binding: SingleCharacterLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: EpisodesEntity?) {

            binding.apply {
                tvNewsText.text = data?.name
                tvDesription.text = data?.gender



                binding.ivNewsImage.setOnClickListener {
                    itemGotoLinkClickListener.invoke(data!!, absoluteAdapterPosition)
                }

                binding.btnShare.setOnClickListener {
                    itemShareClickListener.invoke(data!!, absoluteAdapterPosition)
                }

                Glide.with(this.ivNewsImage)
                    .load(data?.image)
                    .into(ivNewsImage)
            }

        }


    }

    fun setOnGotoClickListener(clickListener: (EpisodesEntity, Int) -> Unit) {
        itemGotoLinkClickListener = clickListener
    }

    fun setOnShareClickListener(clickListener: (EpisodesEntity, Int) -> Unit) {
        itemShareClickListener = clickListener
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