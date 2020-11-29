package com.manugmoya.bicimadstations.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.manugmoya.bicimadstations.ui.common.CoroutineScopeActivity
import com.manugmoya.bicimadstations.databinding.ActivityMainBinding
import com.manugmoya.bicimadstations.model.LocationRepository
import com.manugmoya.bicimadstations.model.StationsRepository
import com.manugmoya.bicimadstations.ui.detail.DetailActivity
import com.manugmoya.bicimadstations.ui.common.orderListByLocation
import com.manugmoya.bicimadstations.ui.common.startActivity
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : CoroutineScopeActivity() {

    private val locationRepository: LocationRepository by lazy { LocationRepository(this) }
    private val stationRepository: StationsRepository by lazy { StationsRepository() }

    private val adapter = StationsAdapter(this) { station ->
        // Usando función reifield
        startActivity<DetailActivity> {
            putExtra(DetailActivity.STATION, station)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvStations.adapter = adapter

        launch {
            binding.progress.visibility = View.VISIBLE
            val location = async { locationRepository.getLocation() }
            val stationList = async { stationRepository.getDataStations() }

            stationList.await()?.let { stationsList ->
                adapter.stationsList =
                    stationsList.orderListByLocation(
                        location.await()
                    )
            } ?: run {
                Toast.makeText(
                    this@MainActivity,
                    "Ha habido un error al recuperar los datos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.progress.visibility = View.GONE
        }
    }

}