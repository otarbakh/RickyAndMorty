package com.otarbakh.rickyandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity
import com.otarbakh.rickyandmorty.databinding.SingleLocationBinding

class LocationAdapter :
    PagingDataAdapter<LocationsEntity, LocationAdapter.LocationViewHolder>(
        LocationDiffCallback()
    ) {


    private lateinit var itemGotoLinkClickListener: (LocationsEntity, Int) -> Unit
    private lateinit var itemShareClickListener: (LocationsEntity, Int) -> Unit

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {

        val data = getItem(position)

        holder.bind(data)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            SingleLocationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    inner class LocationViewHolder(
        private val binding: SingleLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: LocationsEntity?) {

            binding.apply {
                tvlocationName.text = data!!.name
                tvLocationType.text = data!!.type
            }
            binding.mainLayout.setOnClickListener {
                itemGotoLinkClickListener.invoke(data!!, absoluteAdapterPosition)
            }
        }
    }
    fun setOnGotoClickListener(clickListener: (LocationsEntity, Int) -> Unit) {
        itemGotoLinkClickListener = clickListener
    }

    fun setOnShareClickListener(clickListener: (LocationsEntity, Int) -> Unit) {
        itemShareClickListener = clickListener
    }

    private class LocationDiffCallback : DiffUtil.ItemCallback<LocationsEntity>() {
        override fun areItemsTheSame(oldItem: LocationsEntity, newItem: LocationsEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LocationsEntity, newItem: LocationsEntity): Boolean {
            return oldItem == newItem
        }
    }


}