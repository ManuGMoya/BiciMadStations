package com.manugmoya.bicimadstations

import android.app.Application
import com.manugmoya.bicimadstations.di.DaggerMyStationsComponent
import com.manugmoya.bicimadstations.di.MyStationsComponent

class StationApp : Application() {

    lateinit var component: MyStationsComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerMyStationsComponent
            .factory()
            .create(this)
    }
}