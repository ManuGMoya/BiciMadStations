package com.manugmoya.bicimadstations.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [StationDB::class, Favorite::class], version = 1)
@TypeConverters(Converters::class)
abstract class StationDatabase : RoomDatabase() {

    abstract fun stationDao(): StationDao
}