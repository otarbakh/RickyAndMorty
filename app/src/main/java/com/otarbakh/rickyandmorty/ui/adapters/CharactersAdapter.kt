package com.otarbakh.rickyandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.databinding.SingleCharacterLayoutBinding

class CharactersAdapter :
    PagingDataAdapter<CharactersEntity, CharactersAdapter.CharactersVIewHolder>(
        PlayersDiffCallback()
    ) {


    private lateinit var itemGotoLinkClickListener: (CharactersEntity, Int) -> Unit
    private lateinit var itemShareClickListener: (CharactersEntity, Int) -> Unit

    override fun onBindViewHolder(holder: CharactersVIewHolder, position: Int) {

        val data = getItem(position)

        holder.bind(data)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersVIewHolder {

        return CharactersVIewHolder(
            SingleCharacterLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    inner class CharactersVIewHolder(
        private val binding: SingleCharacterLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CharactersEntity?) {

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

    fun setOnGotoClickListener(clickListener: (CharactersEntity, Int) -> Unit) {
        itemGotoLinkClickListener = clickListener
    }

    fun setOnShareClickListener(clickListener: (CharactersEntity, Int) -> Unit) {
        itemShareClickListener = clickListener
    }




    private class PlayersDiffCallback : DiffUtil.ItemCallback<CharactersEntity>() {
        override fun areItemsTheSame(oldItem: CharactersEntity, newItem: CharactersEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharactersEntity, newItem: CharactersEntity): Boolean {
            return oldItem == newItem
        }
    }


}