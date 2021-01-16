package com.manugmoya.bicimadstations.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.manugmoya.bicimadstations.ui.common.ScopedViewModel
import com.manugmoya.domain.StationDomain
import com.manugmoya.usecases.DeleteFavorite
import com.manugmoya.usecases.FindStationById
import com.manugmoya.usecases.InsertFavorite
import com.manugmoya.usecases.IsFavorite
import kotlinx.coroutines.launch

class DetailViewModel(
    private val stationId: Long,
    private val findStationById: FindStationById,
    private val isFavorite: IsFavorite,
    private val deleteFavorite: DeleteFavorite,
    private val insertFavorite: InsertFavorite
) : ScopedViewModel() {

    class UiModel(val station: StationDomain)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findStation()
            return _model
        }

    private var _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> = _favorite

    private fun findStation() = launch {
        _model.value = UiModel(findStationById.invoke(stationId))
        setFavorite()
    }

    fun onFavoriteClicked() = launch {
        val isFavorite = isFavorite.invoke(stationId)

        if (isFavorite != null) {
            deleteFavorite.invoke(stationId)
            _favorite.value = false
        } else {
            insertFavorite.invoke(stationId)
            _favorite.value = true
        }

    }

    private fun setFavorite() = launch {
        val isFavorite = isFavorite.invoke(stationId)
        _favorite.value = isFavorite != null
    }
}