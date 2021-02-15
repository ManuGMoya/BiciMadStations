package com.manugmoya.bicimadstations

import com.manugmoya.bicimadstations.data.EMAIL
import com.manugmoya.bicimadstations.data.PASSWORD
import com.manugmoya.bicimadstations.data.database.StationDatabase
import com.manugmoya.data.repository.PermissionChecker
import com.manugmoya.data.source.LocalDataSource
import com.manugmoya.data.source.LocationDataSource
import com.manugmoya.data.source.RemoteDatasource
import com.manugmoya.domain.FavoriteDomain
import com.manugmoya.domain.LocationDomain
import com.manugmoya.domain.StationDomain
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single(named("email")) { EMAIL }
    single(named("password")) { PASSWORD }
    single { StationDatabase.build(get()) }
    single<LocalDataSource> { FakeLocalDataSource() }
    single<RemoteDatasource> { FakeRemoteDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

val defaultFakeStations = listOf(
    mockedStation.copy(id = 1),
    mockedStation.copy(id = 2),
    mockedStation.copy(id = 3),
    mockedStation.copy(id = 4)
)

class FakeLocalDataSource : LocalDataSource {

    var stationList: List<StationDomain> = emptyList()

    var favList: MutableList<FavoriteDomain> = mutableListOf()

    override suspend fun insertStations(stations: List<StationDomain>) {
        this.stationList = stations
    }

    override suspend fun getStations(): List<StationDomain> =
        stationList

    override suspend fun findById(id: Long): StationDomain {
        return stationList.first { it.id == id }
    }

    override suspend fun insertFav(fav: FavoriteDomain) {
        favList.add(fav)
    }

    override suspend fun deleteFav(fav: FavoriteDomain) {
        favList.remove(fav)
    }

    override suspend fun isFavorite(id: Long): FavoriteDomain? {
        return favList.firstOrNull { it.id == id }
    }
}

class FakeRemoteDataSource : RemoteDatasource {

    var stationList = defaultFakeStations

    override suspend fun getToken(email: String, password: String): String? = "123456"

    override suspend fun getDataStations(apiKey: String): List<StationDomain> = stationList
}

class FakeLocationDataSource : LocationDataSource {

    var location = mockedDefaultLocation.copy(1.1, 2.2)

    override suspend fun getLocation(): LocationDomain? = location


}

class FakePermissionChecker : PermissionChecker {

    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}

