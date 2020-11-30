package com.manugmoya.bicimadstations.ui.detail

import com.manugmoya.bicimadstations.model.Station
import com.manugmoya.bicimadstations.ui.common.Scope

class DetailPresenter : Scope by Scope.Impl(){


    private var view : View? = null

    interface View {
        fun updateUI(station: Station?)
    }


    fun onCreate(view: View, station: Station?) {
        initScope()
        this.view = view
        view.updateUI(station)
    }

    fun onDestroy() {
        cancelScope()
    }

}