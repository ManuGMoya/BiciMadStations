package com.manugmoya.bicimadstations.ui.detail

import com.manugmoya.data.repository.StationRepository
import com.manugmoya.usecases.DeleteFavorite
import com.manugmoya.usecases.FindStationById
import com.manugmoya.usecases.InsertFavorite
import com.manugmoya.usecases.IsFavorite
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailActivityModule(private val stationId: Long){

    @Provides
    fun detailViewModelProvider(
        findStationById: FindStationById,
        insertFavorite: InsertFavorite,
        deleteFavorite: DeleteFavorite,
        isFavorite: IsFavorite
    ) :DetailViewModel {
        return DetailViewModel( stationId, findStationById, insertFavorite, deleteFavorite, isFavorite)
    }

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

@Subcomponent(modules = [DetailActivityModule::class])
interface DetailActivityComponent{
    val detailViewModel: DetailViewModel
}