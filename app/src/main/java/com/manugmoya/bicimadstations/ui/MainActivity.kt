package com.manugmoya.bicimadstations.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import com.manugmoya.bicimadstations.common.CoroutineScopeActivity
import com.manugmoya.bicimadstations.databinding.ActivityMainBinding
import com.manugmoya.bicimadstations.model.EMAIL
import com.manugmoya.bicimadstations.model.PASSWORD
import com.manugmoya.bicimadstations.model.Station
import com.manugmoya.bicimadstations.model.StationsDb
import kotlinx.coroutines.*
import kotlin.coroutines.resume

class MainActivity : CoroutineScopeActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var location: Location

    private val adapter = StationsAdapter { station ->
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.STATION, station)

        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.rvStations.adapter = adapter

        launch(Dispatchers.IO) {
            val locationDeferred = async { getLocation() }
            location = locationDeferred.await()

            val tokenResponse = async { StationsDb.service.getToken(EMAIL, PASSWORD) }

            tokenResponse.await().data?.get(0)?.accessToken?.let { token ->
                val stations = StationsDb.service.getStation(token)
                withContext(Dispatchers.Main) {
                    adapter.stationsList = orderListByLocation(stations.data)
                }
            }
        }
    }

    fun orderListByLocation(data: List<Station>): List<Station> {
        data.forEach {
            val stationLocation = Location("").apply {
                longitude = it.geometry.coordinates[0]
                latitude = it.geometry.coordinates[1]
            }
            it.distanceTo = location.distanceTo(stationLocation)
        }
        return data.sortedBy { it.distanceTo }
    }

    private suspend fun getLocation(): Location {
        val targetLocation = Location("")
        targetLocation.latitude = 0.0
        targetLocation.longitude = 0.0

        val success = requestCoarseLocationPermission()
        return if (success) {
            findLastLocation() ?: targetLocation
        } else {
            targetLocation
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun findLastLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }
        }

    private suspend fun requestCoarseLocationPermission(): Boolean =
        suspendCancellableCoroutine { continuation ->
            Dexter
                .withActivity(this@MainActivity)
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        continuation.resume(true)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        continuation.resume(false)
                    }
                }
                ).check()
        }
}