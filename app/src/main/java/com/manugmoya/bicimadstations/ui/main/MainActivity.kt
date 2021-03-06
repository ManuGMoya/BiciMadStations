package com.manugmoya.bicimadstations.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.manugmoya.bicimadstations.databinding.ActivityMainBinding
import com.manugmoya.bicimadstations.ui.common.GPS_REQUEST
import com.manugmoya.bicimadstations.ui.common.GpsUtils
import com.manugmoya.bicimadstations.ui.common.GpsUtils.OnGpsListener
import com.manugmoya.bicimadstations.ui.common.PermissionRequester
import com.manugmoya.bicimadstations.ui.common.startActivity
import com.manugmoya.bicimadstations.ui.detail.DetailActivity
import com.manugmoya.bicimadstations.ui.main.MainViewModel.UiModel.*
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ScopeActivity() {

    private var isGPS: Boolean = false
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
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