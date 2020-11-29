package com.manugmoya.bicimadstations.model

import android.Manifest
import android.app.Activity
import android.location.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepository(activity: Activity) {
    companion object {
        const val LATITUDE_MADRID_CENTER = 40.41831
        const val LONGITUDE_MADRID_CENTER = -3.70275
    }

    private val locationDataSource = PlayServicesLocationDataSource(activity)

    private val permissionChecker =
        PermissionChecker(activity, Manifest.permission.ACCESS_COARSE_LOCATION)

    suspend fun getLocation(): Location {
        return withContext(Dispatchers.IO) {
            val targetLocation = Location("")
            targetLocation.latitude = LATITUDE_MADRID_CENTER
            targetLocation.longitude = LONGITUDE_MADRID_CENTER

            val success = permissionChecker.request()
            if (success) {
                locationDataSource.findLastLocation() ?: targetLocation
            } else {
                targetLocation
            }
        }
    }

}
