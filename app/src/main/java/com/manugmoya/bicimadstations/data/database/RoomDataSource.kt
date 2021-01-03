package com.manugmoya.bicimadstations.data.database

import com.manugmoya.bicimadstations.data.toFavoriteDomain
import com.manugmoya.bicimadstations.data.toFavoriteRoom
import com.manugmoya.bicimadstations.data.toStationDomain
import com.manugmoya.bicimadstations.data.toStationRoom
import com.manugmoya.data.source.LocalDataSource
import com.manugmoya.domain.FavoriteDomain
import com.manugmoya.domain.StationDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RoomDataSource(db : StationDatabase) : LocalDataSource {

    private val stationDao = db.stationDao()

    override suspend fun insertStations(stations: List<StationDomain>) {
        withContext(Dispatchers.IO) { stationDao.insertStations(stations.map { it.toStationRoom() }) }
    }

    override suspend fun getStations(): List<StationDomain> =
        withContext(Dispatchers.IO) { stationDao.getall().map { it.toStationDomain() } }

    override suspend fun findById(id: Long): StationDomain =
        withContext(Dispatchers.IO){ stationDao.findById(id).toStationDomain()}

    override suspend fun insertFav(fav: FavoriteDomain) =
        withContext(Dispatchers.IO){ stationDao.insertFavorite(fav.toFavoriteRoom())}

    override suspend fun deleteFav(fav: FavoriteDomain) =
        withContext(Dispatchers.IO) { stationDao.deleteFavorite(fav.toFavoriteRoom())}

    override suspend fun isFavorite(id: Long): FavoriteDomain? {
        return withContext(Dispatchers.IO) { stationDao.findFavById(id)?.toFavoriteDomain() }
    }
}



