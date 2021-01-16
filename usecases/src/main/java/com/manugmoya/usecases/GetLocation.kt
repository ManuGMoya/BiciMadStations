package com.manugmoya.usecases

import com.manugmoya.data.repository.LocationRepository
import com.manugmoya.domain.LocationDomain

class GetLocation(private val locationRepository: LocationRepository) {

    suspend fun invoke(): LocationDomain {
        return locationRepository.getLocation()
    }
}