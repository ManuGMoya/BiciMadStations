package com.manugmoya.bicimadstations.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import com.manugmoya.bicimadstations.R
import com.manugmoya.bicimadstations.appendInfo
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

            binding.stationDetailInfo.setStation(this)

            when(light){
                0 -> {binding.stationDetailInfo.setBackgroundResource(R.color.green_700)
                    binding.root.setBackgroundResource(R.color.green_700)}
                1 -> {binding.stationDetailInfo.setBackgroundResource(R.color.orange_700)
                    binding.root.setBackgroundResource(R.color.orange_700)}
                2 -> {binding.stationDetailInfo.setBackgroundResource(R.color.yellow_700)
                    binding.root.setBackgroundResource(R.color.yellow_700)}
                3 -> {binding.stationDetailInfo.setBackgroundResource(R.color.red_700)
                    binding.root.setBackgroundResource(R.color.red_700)}
            }
            if(freeBases == 0){
                binding.stationDetailInfo.setBackgroundResource(R.color.red_700)
                binding.root.setBackgroundResource(R.color.red_700)
            }

            binding.fab.setOnClickListener {
                Toast.makeText(this@DetailActivity, name, Toast.LENGTH_LONG).show()
            }
        }
    }

}


