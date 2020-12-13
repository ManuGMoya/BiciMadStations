package com.manugmoya.bicimadstations.model.server

import com.manugmoya.bicimadstations.StationApp
import com.manugmoya.bicimadstations.model.EMAIL
import com.manugmoya.bicimadstations.model.PASSWORD
import com.manugmoya.bicimadstations.model.database.StationDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StationsRepository(application: StationApp) {

    private val db = application.db

    private suspend fun getToken(): String? {
        return try {
            val tokenResponse = StationsDb.service.getToken(EMAIL, PASSWORD)
            tokenResponse.data?.get(0)?.accessToken
        }catch (e: Exception){
            null
        }
    }

    suspend fun getDataStations(): List<StationDB>? = withContext(Dispatchers.IO){

        val stations = getToken()?.let { StationsDb.service.getStation(it).data }

        stations?.map {  station ->
            station.convertToStationDb()
        }?.let { db.stationDao().insertStations(it) }

        db.stationDao().getall()
    }

    suspend fun findById(id: Long) : StationDB = withContext(Dispatchers.IO) {
        db.stationDao().findById(id)
    }

}
private fun Station.convertToStationDb() =
    StationDB(
        id,
        distanceTo,
        name,
        light,
        number,
        address,
        activate,
        noAvailable,
        totalBases,
        dockBikes,
        freeBases,
        reservationsCount,
        geometry,
        false
    )