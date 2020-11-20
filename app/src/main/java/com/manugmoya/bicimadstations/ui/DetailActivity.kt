package com.manugmoya.bicimadstations.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.manugmoya.bicimadstations.R
import com.manugmoya.bicimadstations.databinding.ActivityDetailBinding
import com.manugmoya.bicimadstations.model.Station

class DetailActivity : AppCompatActivity() {

    companion object {
        const val STATION = "DetailActivity:station"
    }

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Station>(STATION)?.run {

            supportActionBar?.title = name

            binding.stationDetailInfo.text = buildSpannedString {

                bold { append("Address: ") }
                appendLine(address)

                bold { append("Available station: ") }
                val available = if (noAvailable == 0){
                    "Station Enabled"
                }else{
                    "Station Disabled"
                }
                appendLine(available)

                bold { append("Total bases: ") }
                appendLine(totalBases.toString())

                bold { append("Free bases: ") }
                appendLine(freeBases.toString())

                bold { append("Bikes in bases: ") }
                appendLine(dockBikes.toString())

                bold { append("Number of reservations: ") }
                appendLine(reservationsCount.toString())

            }

            when(light){
                0 -> {binding.stationDetailInfo.setBackgroundResource(R.color.green_700)}
                1 -> {binding.stationDetailInfo.setBackgroundResource(R.color.orange_700)}
                2 -> {binding.stationDetailInfo.setBackgroundResource(R.color.yellow_700)} 
                3 -> {binding.stationDetailInfo.setBackgroundResource(R.color.red_700)}
            }
            if(freeBases == 0){
                binding.root.setBackgroundResource(R.color.red_700)
            }
        }
    }
}


