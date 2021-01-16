package com.manugmoya.bicimadstations.data.server

import com.manugmoya.bicimadstations.data.toStationDomain
import com.manugmoya.data.source.RemoteDataSource
import com.manugmoya.domain.StationDomain

class TheStationDbDataSource : RemoteDataSource {

    override suspend fun getToken(email: String, password: String): String? {
        return try {
            val tokenResponse = StationsDb.service.getToken(email, password)
            tokenResponse.data?.get(0)?.accessToken
        }catch (e: Exception){
            null
        }

    }

    override suspend fun getDataStations(apiKey: String): List<StationDomain> {
        return StationsDb.service.getStation(apiKey).data.map {  it.toStationDomain() }
    }
}