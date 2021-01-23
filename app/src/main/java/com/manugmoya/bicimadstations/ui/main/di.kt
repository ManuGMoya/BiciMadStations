package com.manugmoya.bicimadstations.ui.main

import com.manugmoya.data.repository.LocationRepository
import com.manugmoya.data.repository.StationRepository
import com.manugmoya.usecases.GetDataStations
import com.manugmoya.usecases.GetLocation
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainActivityModule {

    // ViewModel
    @Provides
    fun mainViewModelProvider(getLocation: GetLocation, getDataStations: GetDataStations) =
        MainViewModel(getLocation, getDataStations)

    // UseCases
    @Provides
    fun getDataStationsProvider(stationRepository: StationRepository) =
        GetDataStations(stationRepository)

    @Provides
    fun getLocationProvider(locationRepository: LocationRepository) =
        GetLocation(locationRepository)
}

@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent{
    val mainViewModel: MainViewModel
}