package com.manugmoya.bicimadstations.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manugmoya.bicimadstations.model.Station
import com.manugmoya.bicimadstations.ui.common.Scope

class DetailViewModel(private val station: Station) : ViewModel() ,Scope by Scope.Impl(){

    class UiModel(val station: Station)

    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel>
        get() {
            if(_model.value == null) _model.value = UiModel(station)
            return _model
        }

    init {
        initScope()
    }

    override fun onCleared() {
        cancelScope()
    }

}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val station: Station) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DetailViewModel(station) as T

}