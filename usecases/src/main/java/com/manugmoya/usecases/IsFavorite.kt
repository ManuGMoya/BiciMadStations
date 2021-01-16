package com.manugmoya.usecases

import com.manugmoya.data.repository.StationRepository
import com.manugmoya.domain.FavoriteDomain

class IsFavorite(private val stationRepository: StationRepository) {

    suspend fun invoke(id: Long) :FavoriteDomain? =
        stationRepository.isFavorite(id)

}