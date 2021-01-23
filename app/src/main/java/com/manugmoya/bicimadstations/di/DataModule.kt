package com.manugmoya.bicimadstations.di

import com.manugmoya.data.repository.LocationRepository
import com.manugmoya.data.repository.PermissionChecker
import com.manugmoya.data.repository.StationRepository
import com.manugmoya.data.source.LocalDataSource
import com.manugmoya.data.source.LocationDataSource
import com.manugmoya.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun locationRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = LocationRepository(locationDataSource, permissionChecker)

    @Provides
    fun stationRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        @Named("email") email: String,
        @Named("password") password: String
    ) = StationRepository(localDataSource, remoteDataSource, email, password)
}