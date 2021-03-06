package com.manugmoya.data.source

import com.manugmoya.domain.StationDomain

interface RemoteDatasource {
    suspend fun getToken(email: String, password: String): String?
    suspend fun getDataStations(apiKey: String): List<StationDomain>
}