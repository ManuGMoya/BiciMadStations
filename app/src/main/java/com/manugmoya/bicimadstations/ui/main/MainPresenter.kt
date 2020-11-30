package com.manugmoya.bicimadstations.ui.main

import com.manugmoya.bicimadstations.model.LocationRepository
import com.manugmoya.bicimadstations.model.Station
import com.manugmoya.bicimadstations.model.StationsRepository
import com.manugmoya.bicimadstations.ui.common.Scope
import com.manugmoya.bicimadstations.ui.common.orderListByLocation
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainPresenter(
    private val locationRepository: LocationRepository,
    private val stationsRepository: StationsRepository
) : Scope by Scope.Impl() {

    private var view: View? = null

    interface View {
        fun showProgress()
        fun hideProgress()
        fun updateData(stations: List<Station>?)
        fun navigateToDetail(station: Station)
    }

    fun onCreate(view: View) {
        initScope()
        this.view = view

        launch {
            view.showProgress()
            val location = async { locationRepository.getLocation()}
            val stations = async { stationsRepository.getDataStations() }
            view.updateData(stations.await()?.orderListByLocation(location.await()))
            view.hideProgress()
        }
    }

    fun onDestroy() {
        cancelScope()
        this.view = null
    }

    fun onStationClicked(station: Station) {
        view?.navigateToDetail(station)
    }

}