package com.manugmoya.data.repository

import com.manugmoya.data.source.LocationDataSource
import com.manugmoya.domain.LocationDomain


class LocationRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        const val LATITUDE_MADRID_CENTER = 40.41831
        const val LONGITUDE_MADRID_CENTER = -3.70275
    }

    suspend fun getLocation() : LocationDomain {
        val targetLocation = LocationDomain(0.0,0.0)
        targetLocation.latitude = LATITUDE_MADRID_CENTER
        targetLocation.longitude = LONGITUDE_MADRID_CENTER
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.getLocation() ?: targetLocation
        }else{
            targetLocation
        }

    }

}


interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}