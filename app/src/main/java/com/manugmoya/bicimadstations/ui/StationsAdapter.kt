package com.manugmoya.bicimadstations.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.manugmoya.bicimadstations.R
import com.manugmoya.bicimadstations.databinding.ItemStationBinding
import com.manugmoya.bicimadstations.model.Station
import kotlin.properties.Delegates

class StationsAdapter(
    private val context: Context,
    private val listener: (Station) -> Unit
) : RecyclerView.Adapter<StationsAdapter.ItemViewHolder>() {

    var stationsList: List<Station> by Delegates.observable(emptyList()) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition].id == new[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition] == new[newItemPosition]

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size
        }).dispatchUpdatesTo(this)

    }

    override fun getItemCount(): Int = stationsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_station, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val station = stationsList[position]
        holder.bind(station)
        holder.itemView.setOnClickListener {
            listener(station)
        }
    }



    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemStationBinding.bind(view)
        fun bind(station: Station) = with(binding){
            tvStationTitle.text = "${context.getString(R.string.station_name)}: "
            tvStationAddressTitle.text = "${context.getString(R.string.station_address)}: "
            tvStationName.text = station.name
            tvStationAddressValue.text = station.address
            when(station.light){
                0 -> {binding.root.setBackgroundResource(R.color.green_700)}
                1 -> {binding.root.setBackgroundResource(R.color.orange_700)}
                2 -> {binding.root.setBackgroundResource(R.color.yellow_700)}
                3 -> {binding.root.setBackgroundResource(R.color.red_700)}
            }
            if(station.freeBases == 0){
                binding.root.setBackgroundResource(R.color.red_700)
            }
        }
    }



}