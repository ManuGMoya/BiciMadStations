package com.manugmoya.data.source

import com.manugmoya.domain.FavoriteDomain
import com.manugmoya.domain.StationDomain

interface LocalDataSource {
    suspend fun insertStations(stations: List<StationDomain>)
    suspend fun getStations(): List<StationDomain>
    suspend fun findById(id: Long): StationDomain
    suspend fun insertFav(fav: FavoriteDomain)
    suspend fun deleteFav(fav: FavoriteDomain)
    suspend fun isFavorite(id: Long) : FavoriteDomain?
}