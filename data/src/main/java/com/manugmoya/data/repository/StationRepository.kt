package com.manugmoya.data.repository

import com.manugmoya.data.source.LocalDataSource
import com.manugmoya.data.source.RemoteDataSource
import com.manugmoya.domain.FavoriteDomain
import com.manugmoya.domain.StationDomain

class StationRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val email: String,
    private val password: String
) {

    private suspend fun getToken(): String? {
        return try {
            remoteDataSource.getToken(email, password)
        }catch (e: Exception){
            null
        }
    }

    suspend fun getDataStations(): List<StationDomain> {

        val stations = getToken()?.let { remoteDataSource.getDataStations(it) }
        stations?.let { localDataSource.insertStations(it) }

        return localDataSource.getStations()

    }

    suspend fun findById(id: Long) : StationDomain =
        localDataSource.findById(id)

    suspend fun insertFav(id: Long) = localDataSource.insertFav(FavoriteDomain(id))

    suspend fun deleteFav(id: Long) = localDataSource.deleteFav(FavoriteDomain(id))

    suspend fun isFavorite(id: Long) = localDataSource.isFavorite(id)
}
