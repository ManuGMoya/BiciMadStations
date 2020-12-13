package com.manugmoya.bicimadstations.model.database

import androidx.room.*

@Dao
interface StationDao {

    @Query("SELECT * FROM StationDB")
    fun getall(): List<StationDB>

    @Query("SELECT * FROM StationDB WHERE id = :id")
    fun findById(id: Long): StationDB

    @Query("SELECT COUNT(id) FROM StationDB")
    fun stationCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStations(stations: List<StationDB>)

    @Update
    fun updateStation(station: StationDB)

}