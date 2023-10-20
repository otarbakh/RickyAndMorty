package com.otarbakh.rickyandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.otarbakh.rickyandmorty.R
import com.otarbakh.rickyandmorty.data.model.episodes.multipleepisode.MultipleEpisodesDtoItem
import com.otarbakh.rickyandmorty.data.model.locations.mutliplecharacters.MultipleCharactersDto
import com.otarbakh.rickyandmorty.data.model.locations.mutliplecharacters.MultipleCharactersDtoItem
import com.otarbakh.rickyandmorty.databinding.SingleCharacterLayoutBinding
import com.otarbakh.rickyandmorty.databinding.SingleEpisodesLayoutBinding


class SingleLocationCharactersAdapter :
    ListAdapter<MultipleCharactersDtoItem, SingleLocationCharactersAdapter.SingleCharacterEpisodesViewHolder>(
        CharsDiffCallback()
    ) { 
    override fun onBindViewHolder(holder: SingleCharacterEpisodesViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleCharacterEpisodesViewHolder {
        return SingleCharacterEpisodesViewHolder(
            SingleCharacterLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class SingleCharacterEpisodesViewHolder(
        private val binding: SingleCharacterLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MultipleCharactersDtoItem) {

            binding.apply {
                Glide.with(binding.ivNewsImage)
                    .load(data?.image)
                    .transform(CenterInside(), RoundedCorners(24))
                    .into(binding.ivNewsImage)

                tvNewsText.text = data!!.name
                tvDeadOrAlive.text = data!!.status

                if(data!!.status == "Dead"){
                    ivStatus.setImageResource(R.drawable.red_status)
                }
                else if (data!!.status == "Alive"){
                    ivStatus.setImageResource(R.drawable.green_status)
                }
                else{
                    ivStatus.setImageResource(R.drawable.baseline_question_mark_24)
                }
            }

        }
    }
    private class CharsDiffCallback : DiffUtil.ItemCallback<MultipleCharactersDtoItem>() {
        override fun areItemsTheSame(oldItem: MultipleCharactersDtoItem, newItem: MultipleCharactersDtoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MultipleCharactersDtoItem, newItem: MultipleCharactersDtoItem): Boolean {
            return oldItem == newItem
        }
    }
}