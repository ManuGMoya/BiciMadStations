package com.manugmoya.bicimadstations.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.manugmoya.bicimadstations.databinding.ActivityMainBinding
import com.manugmoya.bicimadstations.model.LocationRepository
import com.manugmoya.bicimadstations.model.Station
import com.manugmoya.bicimadstations.model.StationsRepository
import com.manugmoya.bicimadstations.ui.common.startActivity
import com.manugmoya.bicimadstations.ui.detail.DetailActivity

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private lateinit var binding: ActivityMainBinding

    private val presenter by lazy {  MainPresenter( LocationRepository(this), StationsRepository()) }

    private val adapter = StationsAdapter(this) { station ->
        presenter.onStationClicked(station)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.onCreate(this)

        binding.rvStations.adapter = adapter
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }


    override fun updateData(stations: List<Station>?) {
        stations?.let { stationsList ->
            adapter.stationsList =
                stationsList
        } ?: run {
            Toast.makeText(
                this@MainActivity,
                "Ha habido un error al recuperar los datos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun navigateToDetail(station: Station) {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.STATION, station)
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}