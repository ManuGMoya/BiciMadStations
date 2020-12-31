package com.manugmoya.bicimadstations.model

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.manugmoya.data.source.LocationDataSource
import com.manugmoya.domain.LocationDomain
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PlayServicesLocationDataSourcee(application: Application) : LocationDataSource  {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): LocationDomain? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result?.toLocationDomain())
                }
        }
}

fun Location.toLocationDomain(): LocationDomain =
    LocationDomain(
        this.latitude,
        this.longitude
    )
