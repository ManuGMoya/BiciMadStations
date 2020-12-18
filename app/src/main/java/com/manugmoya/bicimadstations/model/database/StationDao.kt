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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStations(stations: List<StationDB>)

    @Update
    fun updateStation(station: StationDB)

    @Query("SELECT * FROM Favorite")
    fun getallFavs(): MutableList<Favorite>

    @Insert
    fun insertFavorite(fav: Favorite)

    @Delete
    fun deleteFavorite(fav: Favorite)

    @Query("SELECT * FROM Favorite WHERE id = :id")
    fun findFavById(id: Long): Favorite?
}