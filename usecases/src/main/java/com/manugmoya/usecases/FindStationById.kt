package com.manugmoya.usecases

import com.manugmoya.data.repository.StationRepository
import com.manugmoya.domain.StationDomain

class FindStationById(private val stationRepository: StationRepository) {

    suspend fun invoke(id: Long) : StationDomain =
        stationRepository.findById(id)
}