package com.manugmoya.bicimadstations.di

import com.manugmoya.bicimadstations.ui.detail.DetailViewModel
import com.manugmoya.bicimadstations.ui.main.MainViewModel
import com.manugmoya.usecases.*
import dagger.Module
import dagger.Provides

@Module
class ViewModelsModule {

    @Provides
    fun mainViewModelProvider(getLocation: GetLocation, getDataStations: GetDataStations) =
        MainViewModel(getLocation, getDataStations)

    @Provides
    fun detailViewModelProvider(
        findStationById: FindStationById,
        insertFavorite: InsertFavorite,
        deleteFavorite: DeleteFavorite,
        isFavorite: IsFavorite
    ) :DetailViewModel {
        // TODO the stationId needs to be provided.
        return DetailViewModel( -1, findStationById, insertFavorite, deleteFavorite, isFavorite)
    }
}