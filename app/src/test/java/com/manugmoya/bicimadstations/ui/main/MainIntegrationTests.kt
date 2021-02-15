package com.manugmoya.bicimadstations.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.manugmoya.bicimadstations.FakeLocalDataSource
import com.manugmoya.bicimadstations.FakeRemoteDataSource
import com.manugmoya.bicimadstations.defaultFakeStations
import com.manugmoya.bicimadstations.initMockedDi
import com.manugmoya.data.source.LocalDataSource
import com.manugmoya.data.source.RemoteDatasource
import com.manugmoya.domain.StationDomain
import com.manugmoya.usecases.GetDataStations
import com.manugmoya.usecases.GetLocation
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<MainViewModel.UiModel>


    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { MainViewModel(get(), get(), get(), get()) }
            factory { GetLocation(get()) }
            factory { GetDataStations(get()) }
        }

        initMockedDi(vmModule)
        mainViewModel = get()
    }

    @Test
    fun `data is loaded from server when onCoarsePermissionRequest is called`() {
        mainViewModel.model.observeForever(observer)

        mainViewModel.onCoarsePermissionRequest()

        verify(observer).onChanged(MainViewModel.UiModel.Content(defaultFakeStations))
    }

    @Test
    fun `local DataSource has data when onCoarsePermissionRequest is called`() {

        val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.stationList = listOf()

        runBlocking {
            mainViewModel.onCoarsePermissionRequest()

            Assert.assertNotEquals(localDataSource.stationList, listOf<StationDomain>())
        }
    }

    @Test
    fun `local DataSource has same data as remote DataSource when onCoarsePermissionRequest is called`() {

        val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        val remoteDataSource = get<RemoteDatasource>() as FakeRemoteDataSource

        runBlocking {
            mainViewModel.onCoarsePermissionRequest()

            Assert.assertEquals(localDataSource.stationList, remoteDataSource.stationList)
        }
    }
}
