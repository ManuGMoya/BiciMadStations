package com.manugmoya.bicimadstations

import android.app.Application
import com.manugmoya.bicimadstations.data.AndroidPermissionChecker
import com.manugmoya.bicimadstations.data.EMAIL
import com.manugmoya.bicimadstations.data.PASSWORD
import com.manugmoya.bicimadstations.data.PlayServicesLocationDataSource
import com.manugmoya.bicimadstations.data.database.RoomDataSource
import com.manugmoya.bicimadstations.data.database.StationDatabase
import com.manugmoya.bicimadstations.data.server.TheStationDbDatasource
import com.manugmoya.bicimadstations.ui.detail.DetailActivity
import com.manugmoya.bicimadstations.ui.detail.DetailViewModel
import com.manugmoya.bicimadstations.ui.main.MainActivity
import com.manugmoya.bicimadstations.ui.main.MainViewModel
import com.manugmoya.data.repository.LocationRepository
import com.manugmoya.data.repository.PermissionChecker
import com.manugmoya.data.repository.StationRepository
import com.manugmoya.data.source.LocalDataSource
import com.manugmoya.data.source.LocationDataSource
import com.manugmoya.data.source.RemoteDatasource
import com.manugmoya.usecases.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.iniDi() {
    startKoin {
        androidLogger()
        androidContext(this@iniDi)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single(named("email")) { EMAIL }
    single(named("password")) { PASSWORD }
    single { StationDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDatasource> { TheStationDbDatasource() }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    single<CoroutineDispatcher>(named( "uiDispatcher")){ Dispatchers.Main }
    single<CoroutineDispatcher>(named( "defaultDispatcher")){ Dispatchers.Default }
}

private val dataModule = module {
    factory { StationRepository(get(), get(), get(named("email")), get(named("password"))) }
    factory { LocationRepository(get(), get()) }
}

private val scopesModule = module {
    scope<MainActivity> {
        viewModel { MainViewModel(get(), get(), get(named("uiDispatcher")), get(named("defaultDispatcher"))) }
        scoped { GetLocation(get()) }
        scoped { GetDataStations(get()) }
    }

    scope<DetailActivity> {
        viewModel { (id: Long) -> DetailViewModel(id, get(), get(), get(), get(), get(named("uiDispatcher"))) }
        scoped { FindStationById(get()) }
        scoped { InsertFavorite(get()) }
        scoped { DeleteFavorite(get()) }
        scoped { IsFavorite(get()) }
    }
}