package com.manugmoya.bicimadstations.di

import android.app.Application
import androidx.room.Room
import com.manugmoya.bicimadstations.data.AndroidPermissionChecker
import com.manugmoya.bicimadstations.data.EMAIL
import com.manugmoya.bicimadstations.data.PASSWORD
import com.manugmoya.bicimadstations.data.PlayServicesLocationDataSource
import com.manugmoya.bicimadstations.data.database.RoomDataSource
import com.manugmoya.bicimadstations.data.database.StationDatabase
import com.manugmoya.bicimadstations.data.server.TheStationDbDataSource
import com.manugmoya.data.repository.PermissionChecker
import com.manugmoya.data.source.LocalDataSource
import com.manugmoya.data.source.LocationDataSource
import com.manugmoya.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("email")
    fun emailProvider(): String = EMAIL

    @Provides
    @Singleton
    @Named("password")
    fun passwordProvider(): String = PASSWORD

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        StationDatabase::class.java,
        "station.db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: StationDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = TheStationDbDataSource()

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource = PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker = AndroidPermissionChecker(app)
}