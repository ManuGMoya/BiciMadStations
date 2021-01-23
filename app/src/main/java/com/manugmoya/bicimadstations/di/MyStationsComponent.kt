package com.manugmoya.bicimadstations.di

import android.app.Application
import com.manugmoya.bicimadstations.ui.detail.DetailActivityComponent
import com.manugmoya.bicimadstations.ui.detail.DetailActivityModule
import com.manugmoya.bicimadstations.ui.main.MainActivityComponent
import com.manugmoya.bicimadstations.ui.main.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface MyStationsComponent {

    fun plus(module: MainActivityModule): MainActivityComponent
    fun plus(module: DetailActivityModule): DetailActivityComponent

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance app:Application): MyStationsComponent
    }
}