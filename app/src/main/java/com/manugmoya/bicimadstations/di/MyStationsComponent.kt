package com.manugmoya.bicimadstations.di

import android.app.Application
import com.manugmoya.bicimadstations.ui.detail.DetailViewModel
import com.manugmoya.bicimadstations.ui.main.MainViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, UseCasesModule::class, ViewModelsModule::class])
interface MyStationsComponent {

    val mainViewModel: MainViewModel
    val detailViewModel: DetailViewModel

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance app:Application): MyStationsComponent
    }
}