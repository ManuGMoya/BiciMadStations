package com.manugmoya.bicimadstations.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.manugmoya.bicimadstations.ui.common.GpsUtils
import com.manugmoya.bicimadstations.ui.common.GpsUtils.OnGpsListener
import com.manugmoya.bicimadstations.PermissionRequester
import com.manugmoya.bicimadstations.databinding.ActivityMainBinding
import com.manugmoya.bicimadstations.model.AndroidPermissionChecker
import com.manugmoya.bicimadstations.model.EMAIL
import com.manugmoya.bicimadstations.model.PASSWORD
import com.manugmoya.bicimadstations.model.PlayServicesLocationDataSource
import com.manugmoya.bicimadstations.model.database.RoomDataSource
import com.manugmoya.bicimadstations.model.server.TheStationDbDatasource
import com.manugmoya.bicimadstations.ui.common.GPS_REQUEST
import com.manugmoya.bicimadstations.ui.common.app
import com.manugmoya.bicimadstations.ui.common.getViewModel
import com.manugmoya.bicimadstations.ui.common.startActivity
import com.manugmoya.bicimadstations.ui.detail.DetailActivity
import com.manugmoya.bicimadstations.ui.main.MainViewModel.UiModel.*
import com.manugmoya.data.repository.LocationRepository
import com.manugmoya.data.repository.StationRepository
import com.manugmoya.usecases.GetDataStations
import com.manugmoya.usecases.GetLocation

class MainActivity : AppCompatActivity() {

    private var isGPS: Boolean = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: StationsAdapter
    private val coarsePermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GpsUtils(this).turnGPSOn(object : OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                // turn on GPS
                isGPS = isGPSEnable
                setViewModel()
            }
        })
    }

    private fun setViewModel(){
        // Uso de función genérica
        viewModel = getViewModel {

            val localDataSource = RoomDataSource(app.db)

            MainViewModel(
                GetLocation(
                    LocationRepository(
                        PlayServicesLocationDataSource(app),
                        AndroidPermissionChecker(app)
                    )
                ),
                GetDataStations(
                    StationRepository(localDataSource, TheStationDbDatasource(), EMAIL, PASSWORD)
                )
            )
        }

        adapter = StationsAdapter(this, viewModel::onStationClicked)
/*        // Es lo mismo que:
        adapter = StationsAdapter(this) {
            viewModel.onStationClicked(it)
        }*/
        binding.rvStations.adapter = adapter

        viewModel.model.observe(this, Observer(::updateUi))
/*        // Es lo mismo que:
        viewModel.model.observe(this, Observer {
            updateUi(it)
        })*/

        viewModel.navigation.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { station ->
                startActivity<DetailActivity> {
                    putExtra(DetailActivity.STATION, station.id)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPS = true
            }
        }
        setViewModel()
    }

    private fun updateUi(model: MainViewModel.UiModel) {
        binding.progress.visibility = if (model == Loading) View.VISIBLE else View.GONE
        when (model) {
            is Content -> {
                if (model.stations.isEmpty()) {
                    Toast.makeText(
                        this,
                        "No ha sido posible recuperar los datos. Comprueba tu conexión.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                adapter.stationsList = model.stations


            }
            RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequest()
            }
        }
    }

}