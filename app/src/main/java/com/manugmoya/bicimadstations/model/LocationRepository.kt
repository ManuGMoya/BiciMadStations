package com.manugmoya.bicimadstations.model

import android.Manifest
import android.app.Application
import android.location.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepository(application: Application) {
    companion object {
        const val LATITUDE_MADRID_CENTER = 40.41831
        const val LONGITUDE_MADRID_CENTER = -3.70275
    }

    private val locationDataSource = PlayServicesLocationDataSource(application)
    private val coarsePermissionChecker = PermissionChecker(application, Manifest.permission.ACCESS_COARSE_LOCATION)

    suspend fun getLocation(): Location {
        return withContext(Dispatchers.IO) {
            val targetLocation = Location("")
            targetLocation.latitude = LATITUDE_MADRID_CENTER
            targetLocation.longitude = LONGITUDE_MADRID_CENTER

            val success = coarsePermissionChecker.check()
            if (success) {
                locationDataSource.findLastLocation() ?: targetLocation
            } else {
                targetLocation
            }
        }
    }

}
