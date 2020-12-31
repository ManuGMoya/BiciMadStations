package com.manugmoya.usecases

import com.manugmoya.data.repository.StationRepository

class DeleteFavorite(private val stationRepository: StationRepository) {

    suspend fun invoke(id: Long) = stationRepository.deleteFav(id)
}