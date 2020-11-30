package com.manugmoya.bicimadstations.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.manugmoya.bicimadstations.R
import com.manugmoya.bicimadstations.databinding.ActivityDetailBinding
import com.manugmoya.bicimadstations.model.Station

class DetailActivity : AppCompatActivity() , DetailPresenter.View{

    companion object {
        const val STATION = "DetailActivity:station"
    }

    private lateinit var binding : ActivityDetailBinding
    private val presenter = DetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onCreate(this, intent.getParcelableExtra<Station>(STATION))
    }

    override fun updateUI(station: Station?)  {
        station?.let { mStation ->
            supportActionBar?.title = mStation.name

            binding.stationDetailInfo.setStation(mStation)

            when(mStation.light){
                0 -> {binding.stationDetailInfo.setBackgroundResource(R.color.green_700)
                    binding.root.setBackgroundResource(R.color.green_700)}
                1 -> {binding.stationDetailInfo.setBackgroundResource(R.color.orange_700)
                    binding.root.setBackgroundResource(R.color.orange_700)}
                2 -> {binding.stationDetailInfo.setBackgroundResource(R.color.yellow_700)
                    binding.root.setBackgroundResource(R.color.yellow_700)}
                3 -> {binding.stationDetailInfo.setBackgroundResource(R.color.red_700)
                    binding.root.setBackgroundResource(R.color.red_700)}
            }
            if(mStation.freeBases == 0){
                binding.stationDetailInfo.setBackgroundResource(R.color.red_700)
                binding.root.setBackgroundResource(R.color.red_700)
            }

            binding.fab.setOnClickListener {
                Toast.makeText(this@DetailActivity, mStation.name, Toast.LENGTH_LONG).show()
            }
        } ?: run {
            Toast.makeText(
                this,
                "No ha sido posible recuperar la información de la estación.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}


