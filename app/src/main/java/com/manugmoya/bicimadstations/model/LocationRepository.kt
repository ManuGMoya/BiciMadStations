package com.manugmoya.bicimadstations.model

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class LocationRepository(activity: Activity) {
    companion object {
        const val LATITUDE_MADRID_CENTER = 40.41831
        const val LONGITUDE_MADRID_CENTER = -3.70275
    }

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    private val permissionChecker =
        PermissionChecker(activity, Manifest.permission.ACCESS_COARSE_LOCATION)

    suspend fun getLocation(): Location {
        return withContext(Dispatchers.IO) {
            val targetLocation = Location("")
            targetLocation.latitude = LATITUDE_MADRID_CENTER
            targetLocation.longitude = LONGITUDE_MADRID_CENTER

            val success = permissionChecker.request()
            if (success) {
                findLastLocation() ?: targetLocation
            } else {
                targetLocation
            }
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
}
