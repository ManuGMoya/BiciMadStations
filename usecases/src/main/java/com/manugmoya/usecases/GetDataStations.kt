package com.manugmoya.usecases

import com.manugmoya.data.repository.StationRepository
import com.manugmoya.domain.StationDomain

class GetDataStations (private val stationRepository: StationRepository) {

    suspend fun invoke(): List<StationDomain> {
        return stationRepository.getDataStations()
    }
}