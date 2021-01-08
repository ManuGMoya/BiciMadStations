package com.manugmoya.usecases

import com.manugmoya.data.repository.StationRepository

class InsertFavorite (private val stationRepository: StationRepository) {

    suspend fun invoke(id: Long) =
        stationRepository.insertFav(id)
}