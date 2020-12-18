package com.manugmoya.bicimadstations.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manugmoya.bicimadstations.model.database.Favorite
import com.manugmoya.bicimadstations.model.database.StationDB
import com.manugmoya.bicimadstations.model.server.StationsRepository
import com.manugmoya.bicimadstations.ui.common.Event
import com.manugmoya.bicimadstations.ui.common.Scope
import kotlinx.coroutines.launch

class DetailViewModel(private val stationId: Long, private val repository: StationsRepository) : ViewModel() ,Scope by Scope.Impl(){

    class UiModel(val station: StationDB)

    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel>
        get() {
            if(_model.value == null) findStation()
            return _model
        }

    private var _favorite = MutableLiveData<Boolean>()
    val favorite : LiveData<Boolean> = _favorite

    init {
        initScope()
    }

    override fun onCleared() {
        cancelScope()
    }

    private fun findStation() = launch {
        _model.value = UiModel(repository.findById(stationId))
        setFavorite()
    }

    fun onFavoriteClicked() = launch {
        val isFavorite = repository.isFavorite(stationId)

        if(isFavorite != null){
            repository.deleteFav(Favorite(stationId))
            _favorite.value = false
        }else{
            repository.insertFav(Favorite(stationId))
            _favorite.value = true
        }

    }

    private fun setFavorite()= launch {
        val isFavorite = repository.isFavorite(stationId)
        _favorite.value = isFavorite != null
    }
}