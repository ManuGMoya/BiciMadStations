package com.manugmoya.bicimadstations.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manugmoya.bicimadstations.captureValues
import com.manugmoya.bicimadstations.mockedDefaultLocation
import com.manugmoya.bicimadstations.mockedStation
import com.manugmoya.bicimadstations.ui.common.Event
import com.manugmoya.usecases.GetDataStations
import com.manugmoya.usecases.GetLocation
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.qualifier.named
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest2 {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getLocation: GetLocation

    @Mock
    lateinit var getDataStations: GetDataStations

    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(
            getLocation,
            getDataStations,
            Dispatchers.Unconfined,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `observing LiveData launches location permission request`() {

        runBlocking {
            mainViewModel.model.captureValues {
                assertSendsTypes(5, MainViewModel.UiModel.RequestLocationPermission)
            }
        }
    }

    @Test
    fun `after requesting the permission, loading is shown`() {

        runBlocking {
            val stations = listOf(mockedStation.copy(id = 4))
            whenever(getDataStations.invoke()).thenReturn(stations)
            whenever(getLocation.invoke()).thenReturn(mockedDefaultLocation)

            mainViewModel.model.captureValues {
                mainViewModel.onCoarsePermissionRequest()
                assertSendsTypes(5, MainViewModel.UiModel.Loading)
            }
        }
    }

    @Test
    fun `after requesting the permission, getDataStations is called`() {

        runBlocking {
            val stations = listOf(mockedStation.copy(id = 1))
            whenever(getDataStations.invoke()).thenReturn(stations)
            whenever(getLocation.invoke()).thenReturn(mockedDefaultLocation)

            mainViewModel.model.captureValues {
                mainViewModel.onCoarsePermissionRequest()
                assertSendsTypes(5, MainViewModel.UiModel.Loading)
                assertSendsValues(5, MainViewModel.UiModel.Content(stations))
            }
        }
    }

    @Test
    fun `check calls Event when onStationClickedIsCalled`() {

        runBlocking {
            mainViewModel.navigation.captureValues {
                mainViewModel.onStationClicked(mockedStation)
                assertSendsValues(5, Event(mockedStation))
            }
        }
    }

}
