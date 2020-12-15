package com.manugmoya.bicimadstations.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manugmoya.bicimadstations.model.database.Favorite
import com.manugmoya.bicimadstations.model.database.StationDB
import com.manugmoya.bicimadstations.model.server.Station
import com.manugmoya.bicimadstations.model.server.StationsRepository
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

    init {
        initScope()
    }

    override fun onCleared() {
        cancelScope()
    }

    private fun findStation() = launch {
        _model.value = UiModel(repository.findById(stationId))
    }

    fun onFavoriteClicked() = launch {
        _model.value?.station?.let {
            val updatedStation = it.copy(favorite = !it.favorite)

            if(updatedStation.favorite){
                repository.insertFav(Favorite(updatedStation.id))
            }else{
                repository.deleteFav(Favorite(updatedStation.id))
            }

            _model.value = UiModel(updatedStation)
            repository.update(updatedStation)
        }
    }
}