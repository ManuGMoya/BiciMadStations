package com.manugmoya.bicimadstations.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manugmoya.bicimadstations.model.LocationRepository
import com.manugmoya.bicimadstations.model.Station
import com.manugmoya.bicimadstations.model.StationsRepository
import com.manugmoya.bicimadstations.ui.common.Event
import com.manugmoya.bicimadstations.ui.common.Scope
import com.manugmoya.bicimadstations.ui.common.orderListByLocation
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(
    private val locationRepository: LocationRepository,
    private val stationsRepository: StationsRepository
) : ViewModel() ,Scope by Scope.Impl() {

    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel>
        get() {
            if(_model.value == null) refresh()
            return _model
        }

    private val _navigation = MutableLiveData<Event<Station>>()
    val navigation : LiveData<Event<Station>> = _navigation

    sealed class UiModel{
        object Loading : UiModel()
        class Content(val stations : List<Station>) : UiModel()
        object RequestLocationPermission: UiModel()
    }


    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequest() {
        launch {
            _model.value = UiModel.Loading
            val location = async { locationRepository.getLocation()}
            val stations = async { stationsRepository.getDataStations() }
            _model.value = stations.await()?.orderListByLocation(location.await())?.let {
                UiModel.Content(
                    it
                )
            }
        }
    }

    fun onStationClicked(station: Station) {
        _navigation.value = Event(station)
    }

    override fun onCleared() {
        cancelScope()
    }
}
