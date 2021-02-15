package com.manugmoya.bicimadstations

import android.app.Application
import androidx.room.Room
import com.manugmoya.bicimadstations.data.database.StationDatabase

class StationApp: Application() {


    override fun onCreate() {
        super.onCreate()
        iniDi()
    }
}