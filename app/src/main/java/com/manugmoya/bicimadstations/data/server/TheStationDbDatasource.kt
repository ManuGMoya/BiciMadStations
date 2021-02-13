package com.manugmoya.bicimadstations.data.server

import com.manugmoya.bicimadstations.data.EMAIL
import com.manugmoya.bicimadstations.data.PASSWORD
import com.manugmoya.bicimadstations.data.toStationDomain
import com.manugmoya.data.source.RemoteDatasource
import com.manugmoya.domain.StationDomain

class TheStationDbDatasource(private val stationDb: StationsDb) : RemoteDatasource {

    override suspend fun getToken(email: String, password: String): String? {
        return try {
            val tokenResponse = stationDb.service.getToken(EMAIL, PASSWORD)
            tokenResponse.data?.get(0)?.accessToken
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getDataStations(apiKey: String): List<StationDomain> {
        return stationDb.service.getStation(apiKey).data.map { it.toStationDomain() }
    }
}




