package com.manugmoya.bicimadstations.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.manugmoya.bicimadstations.R
import com.manugmoya.bicimadstations.databinding.ActivityDetailBinding
import com.manugmoya.bicimadstations.model.server.Station
import com.manugmoya.bicimadstations.model.server.StationsRepository
import com.manugmoya.bicimadstations.ui.common.app
import com.manugmoya.bicimadstations.ui.common.getViewModel

class DetailActivity : AppCompatActivity(){

    companion object {
        const val STATION = "DetailActivity:station"
    }

    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewModel : DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val stationId = intent.getLongExtra(STATION, -1L)
            ?: throw (IllegalStateException("Station not found"))

/*        viewModel = ViewModelProvider(
            this, DetailViewModelFactory(station)
        )[DetailViewModel::class.java]*/
        viewModel = getViewModel {DetailViewModel(stationId, StationsRepository(app))}

        viewModel.model.observe(this, Observer (::updateUI))
    }

    private fun updateUI(model: DetailViewModel.UiModel) = with(binding) {

        val station = model.station
        supportActionBar?.title = station.name

        binding.stationDetailInfo.setStation(station)

        when (station.light) {
            0 -> {
                updateColor(binding, R.color.green_700)
            }
            1 -> {
                updateColor(binding, R.color.orange_700)
            }
            2 -> {
                updateColor(binding, R.color.yellow_700)
            }
            3 -> {
                updateColor(binding, R.color.red_700)
            }
        }
        if (station.freeBases == 0) {
            updateColor(binding, R.color.red_700)
        }

        fab.setOnClickListener {
            viewModel.onFavoriteClicked()
        }

        val icon = if(station.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        fab.setImageDrawable(getDrawable(icon))
    }

    private fun updateColor(binding : ActivityDetailBinding, color: Int){
        binding.stationDetailInfo.setBackgroundResource(color)
        binding.root.setBackgroundResource(color)
    }
}


