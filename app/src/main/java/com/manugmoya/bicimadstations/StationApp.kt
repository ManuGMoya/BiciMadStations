package com.manugmoya.bicimadstations

import android.app.Application
import androidx.room.Room
import com.manugmoya.bicimadstations.data.database.StationDatabase

class StationApp: Application() {

    lateinit var db: StationDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            StationDatabase::class.java,
            "station.db"
        ).build()
    }
}