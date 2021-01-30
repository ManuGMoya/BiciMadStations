package com.manugmoya.bicimadstations.ui.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import com.manugmoya.bicimadstations.R
import com.manugmoya.bicimadstations.databinding.ActivityDetailBinding
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : ScopeActivity() {

    companion object {
        const val STATION = "DetailActivity:station"
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel() {
        parametersOf(intent.getLongExtra(STATION, -1L))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.model.observe(this, Observer(::updateUI))

        viewModel.favorite.observe(this, { favorite ->
            val icon = if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
            binding.fab.setImageDrawable(getDrawable(icon))
        })
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
    }

    private fun updateColor(binding: ActivityDetailBinding, color: Int) {
        binding.stationDetailInfo.setBackgroundResource(color)
        binding.root.setBackgroundResource(color)
    }
}


