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
        val station: Station = intent.getParcelableExtra(STATION)
            ?: throw (IllegalStateException("Movie not found"))
        presenter.onCreate(this, station)
    }

    override fun updateUI(station: Station) = with(binding) {

        supportActionBar?.title = station.name

        binding.stationDetailInfo.setStation(station)

        when (station.light) {
            0 -> {
                stationDetailInfo.setBackgroundResource(R.color.green_700)
                root.setBackgroundResource(R.color.green_700)
            }
            1 -> {
                stationDetailInfo.setBackgroundResource(R.color.orange_700)
                binding.root.setBackgroundResource(R.color.orange_700)
            }
            2 -> {
                stationDetailInfo.setBackgroundResource(R.color.yellow_700)
                root.setBackgroundResource(R.color.yellow_700)
            }
            3 -> {
                stationDetailInfo.setBackgroundResource(R.color.red_700)
                root.setBackgroundResource(R.color.red_700)
            }
        }
        if (station.freeBases == 0) {
            stationDetailInfo.setBackgroundResource(R.color.red_700)
            root.setBackgroundResource(R.color.red_700)
        }

        fab.setOnClickListener {
            Toast.makeText(this@DetailActivity, station.name, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}


