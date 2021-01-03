package com.manugmoya.bicimadstations.model

import android.annotation.SuppressLint
import android.app.Application
import android.os.Looper
import android.widget.Toast
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.manugmoya.bicimadstations.ui.common.isGPSEnabled
import com.manugmoya.data.source.LocationDataSource
import com.manugmoya.domain.LocationDomain
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PlayServicesLocationDataSource(private val application: Application) : LocationDataSource  {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): LocationDomain? =
        suspendCancellableCoroutine { continuation ->
            val locationRequest = LocationRequest.create()
            locationRequest.interval = 1000
            locationRequest.fastestInterval = 1000
            locationRequest.numUpdates = 1


            if(application.isGPSEnabled()){
                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        continuation.resume(locationResult?.locations?.get(0)?.toLocationDomain())
                    }
                }

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }else{
                Toast.makeText(application, "Para obtener las estaciones cercanas, active el GPS", Toast.LENGTH_LONG).show()
                continuation.resume(null)
            }
        }
}
