package com.manugmoya.bicimadstations.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.manugmoya.bicimadstations.R
import com.manugmoya.bicimadstations.databinding.ActivityDetailBinding
import com.manugmoya.bicimadstations.data.database.RoomDataSource
import com.manugmoya.bicimadstations.data.server.TheStationDbDataSource
import com.manugmoya.bicimadstations.data.toStationRoom
import com.manugmoya.bicimadstations.ui.common.app
import com.manugmoya.bicimadstations.ui.common.getViewModel
import com.manugmoya.usecases.FindStationById
import com.manugmoya.data.repository.StationRepository
import com.manugmoya.usecases.DeleteFavorite
import com.manugmoya.usecases.InsertFavorite
import com.manugmoya.usecases.IsFavorite

class DetailActivity : AppCompatActivity() {

    companion object {
        const val STATION = "DetailActivity:station"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val stationId = intent.getLongExtra(STATION, -1L)


        viewModel = getViewModel {
            val stationsRepository = StationRepository(
                RoomDataSource(app.db),
                TheStationDbDataSource()
            )

            DetailViewModel(
                stationId,
                FindStationById(stationsRepository),
                IsFavorite(stationsRepository),
                DeleteFavorite(stationsRepository),
                InsertFavorite(stationsRepository)
            )
        }

        viewModel.model.observe(this, Observer(::updateUI))

        viewModel.favorite.observe(this, { favorite ->
            val icon = if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
            binding.fab.setImageDrawable(getDrawable(icon))
        })
    }

    private fun updateUI(model: DetailViewModel.UiModel) = with(binding) {

        val station = model.station
        supportActionBar?.title = station.name

        binding.stationDetailInfo.setStation(station.toStationRoom())

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
    }

    private fun updateColor(binding: ActivityDetailBinding, color: Int) {
        binding.stationDetailInfo.setBackgroundResource(color)
        binding.root.setBackgroundResource(color)
    }
}


