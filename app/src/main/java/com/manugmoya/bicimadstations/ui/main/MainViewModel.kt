package com.manugmoya.bicimadstations.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.manugmoya.bicimadstations.data.toLocation
import com.manugmoya.bicimadstations.ui.common.Event
import com.manugmoya.bicimadstations.ui.common.ScopedViewModel
import com.manugmoya.bicimadstations.ui.common.orderListByLocation
import com.manugmoya.domain.StationDomain
import com.manugmoya.usecases.GetDataStations
import com.manugmoya.usecases.GetLocation
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(
    private val getLocation: GetLocation,
    private val getDataStations: GetDataStations
) : ScopedViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel>
        get() {
            if(_model.value == null) refresh()
            return _model
        }

    private val _navigation = MutableLiveData<Event<StationDomain>>()
    val navigation : LiveData<Event<StationDomain>> = _navigation

    sealed class UiModel{
        object Loading : UiModel()
        class Content(val stations : List<StationDomain>) : UiModel()
        object RequestLocationPermission: UiModel()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequest() {
        launch {
            _model.value = UiModel.Loading
            val location = async { getLocation.invoke() }
            val stations = async { getDataStations.invoke() }

            val stationsOrdered =
                stations.await().orderListByLocation(location.await().toLocation())

            _model.value = stationsOrdered.let{
                UiModel.Content(
                    it
                )
            }
        }
    }

    fun onStationClicked(station: StationDomain) {
        _navigation.value = Event(station)
    }

}