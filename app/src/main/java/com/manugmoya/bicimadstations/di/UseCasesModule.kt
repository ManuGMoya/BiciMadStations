package com.manugmoya.bicimadstations.di

import com.manugmoya.data.repository.LocationRepository
import com.manugmoya.data.repository.StationRepository
import com.manugmoya.usecases.*
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun getDataStationsProvider(stationRepository: StationRepository) =
        GetDataStations(stationRepository)

    @Provides
    fun getLocationProvider(locationRepository: LocationRepository) =
        GetLocation(locationRepository)

    @Provides
    fun findStationByIdProvider(stationRepository: StationRepository) =
        FindStationById(stationRepository)

    @Provides
    fun insertFavoriteProvider(stationRepository: StationRepository) =
        InsertFavorite(stationRepository)

    @Provides
    fun deleteFavorite(stationRepository: StationRepository) =
        DeleteFavorite(stationRepository)

    @Provides
    fun isFavorite(stationRepository: StationRepository) =
        IsFavorite(stationRepository)

}