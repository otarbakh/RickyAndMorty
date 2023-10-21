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
import com.otarbakh.rickyandmorty.data.model.characters.CharactersResult
import com.otarbakh.rickyandmorty.databinding.SingleCharacterLayoutBinding

class SearchedCharactersAdapter :
    ListAdapter<CharactersResult, SearchedCharactersAdapter.CharactersVIewHolder>(
        PlayersDiffCallback()
    ) {


    private lateinit var itemGotoLinkClickListener: (CharactersResult, Int) -> Unit
    private lateinit var itemShareClickListener: (CharactersResult, Int) -> Unit

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

        fun bind(data: CharactersResult?) {

            binding.apply {
                tvNewsText.text = data!!.name
                tvDeadOrAlive.text = data.status
                tvGender.text = data.gender

                if (data.status == "Dead") {
                    ivStatus.setImageResource(R.drawable.red_status)
                } else if (data.status == "Alive") {
                    ivStatus.setImageResource(R.drawable.green_status)
                } else {
                    ivStatus.setImageResource(R.drawable.baseline_question_mark_24)
                }

                binding.ivNewsImage.setOnClickListener {
                    itemGotoLinkClickListener.invoke(data, absoluteAdapterPosition)
                }

                Glide.with(this.ivNewsImage)
                    .load(data.image)
                    .transform(CenterInside(), RoundedCorners(24))
                    .into(ivNewsImage)
            }
        }
    }

    fun setOnGotoClickListener(clickListener: (CharactersResult, Int) -> Unit) {
        itemGotoLinkClickListener = clickListener
    }

    private class PlayersDiffCallback : DiffUtil.ItemCallback<CharactersResult>() {
        override fun areItemsTheSame(
            oldItem: CharactersResult,
            newItem: CharactersResult
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharactersResult,
            newItem: CharactersResult
        ): Boolean {
            return oldItem == newItem
        }
    }
}
