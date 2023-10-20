//package com.otarbakh.rickyandmorty.ui.adapters
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.paging.PagingDataAdapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.otarbakh.rickyandmorty.databinding.SingleLocationFilterLayoutBinding
//import com.otarbakh.rickyandmorty.ui.locations.FilterData
//
//class LocationsFilterAdapter :
//    ListAdapter<FilterData, LocationsFilterAdapter.LocationsFilterViewHolder>(
//        LocationDiffCallback()
//    ) {
//
//    private lateinit var itemGotoLinkClickListener: (FilterData, Int) -> Unit
//
//    override fun onBindViewHolder(holder: LocationsFilterViewHolder, position: Int) {
//        val data = getItem(position)
//        holder.bind(data)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsFilterViewHolder {
//        return LocationsFilterViewHolder(
//            SingleLocationFilterLayoutBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false
//            )
//        )
//    }
//
//    inner class LocationsFilterViewHolder(
//        private val binding: SingleLocationFilterLayoutBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(data: FilterData?) {
//            binding.apply {
//                tvType.text = data!!.type
//            }
//            binding.mainLayout.setOnClickListener {
//                itemGotoLinkClickListener.invoke(data!!, absoluteAdapterPosition)
//            }
//        }
//    }
//
//    fun setOnGotoClickListener(clickListener: (FilterData, Int) -> Unit) {
//        itemGotoLinkClickListener = clickListener
//    }
//
//    private class LocationDiffCallback : DiffUtil.ItemCallback<FilterData>() {
//        override fun areItemsTheSame(oldItem: FilterData, newItem: FilterData): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(
//            oldItem: FilterData,
//            newItem: FilterData
//        ): Boolean {
//            return oldItem == newItem
//        }
//    }
//}