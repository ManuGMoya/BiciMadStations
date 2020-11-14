package com.manugmoya.bicimadstations.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manugmoya.bicimadstations.R
import com.manugmoya.bicimadstations.databinding.ItemStationBinding
import com.manugmoya.bicimadstations.model.FakeData

class StationsAdapter (
        private val stationsList : List<FakeData>
) : RecyclerView.Adapter<StationsAdapter.ItemViewHolder>(){

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
    }



    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemStationBinding.bind(view)
        fun bind(station: FakeData) = with(binding){
            tvStationName.text = station.title
            tvStationAddressValue.text = station.address
        }
    }



}