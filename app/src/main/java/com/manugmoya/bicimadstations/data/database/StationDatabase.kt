package com.manugmoya.bicimadstations.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [StationDB::class, Favorite::class], version = 1)
@TypeConverters(Converters::class)
abstract class StationDatabase : RoomDatabase() {

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            StationDatabase::class.java,
            "station.db"
        ).build()
    }

    abstract fun stationDao(): StationDao
}