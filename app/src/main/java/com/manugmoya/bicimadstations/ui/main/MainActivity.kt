package com.manugmoya.bicimadstations.ui.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.manugmoya.bicimadstations.PermissionRequester
import com.manugmoya.bicimadstations.databinding.ActivityMainBinding
import com.manugmoya.bicimadstations.model.LocationRepository
import com.manugmoya.bicimadstations.model.StationsRepository
import com.manugmoya.bicimadstations.ui.common.getViewModel
import com.manugmoya.bicimadstations.ui.common.startActivity
import com.manugmoya.bicimadstations.ui.detail.DetailActivity
import com.manugmoya.bicimadstations.ui.main.MainViewModel.UiModel.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: StationsAdapter
    private val coarsePermissionRequester = PermissionRequester(this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Uso de función genérica
        viewModel = getViewModel { MainViewModel(LocationRepository(application), StationsRepository()) }

/*        // Es lo mismo que
        viewModel = ViewModelProvider(
            this, MainViewModelFactory(
                LocationRepository(this),
                StationsRepository()
            )
        )[MainViewModel::class.java]*/

        adapter = StationsAdapter(this, viewModel::onStationClicked)
/*        // Es lo mismo que:
        adapter = StationsAdapter(this) {
            viewModel.onStationClicked(it)
        }*/
        binding.rvStations.adapter = adapter

        viewModel.model.observe(this, Observer (::updateUi))
/*        // Es lo mismo que:
        viewModel.model.observe(this, Observer {
            updateUi(it)
        })*/
    }

    private fun updateUi(model: MainViewModel.UiModel) {
        binding.progress.visibility = if (model == Loading) View.VISIBLE else View.GONE
        when (model) {
            is Content -> adapter.stationsList = model.stations
            is Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.STATION, model.station)
            }
            RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequest()
            }
        }
    }

}