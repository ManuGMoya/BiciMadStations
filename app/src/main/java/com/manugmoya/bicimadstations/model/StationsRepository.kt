package com.manugmoya.bicimadstations.model

class StationsRepository {

    private suspend fun getToken(): String? {
        val tokenResponse = StationsDb.service.getToken(EMAIL, PASSWORD)
        return tokenResponse.data?.get(0)?.accessToken
    }

    suspend fun getDataStations(): List<Station>? {
        return getToken()?.let { StationsDb.service.getStation(it).data }
    }
}
