package com.manugmoya.bicimadstations.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.manugmoya.bicimadstations.CoroutinesTestRule
import com.manugmoya.bicimadstations.mockedDefaultLocation
import com.manugmoya.bicimadstations.mockedStation
import com.manugmoya.usecases.GetDataStations
import com.manugmoya.usecases.GetLocation
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getLocation: GetLocation

    @Mock
    lateinit var getDataStations: GetDataStations

    @Mock
    lateinit var observer: Observer<MainViewModel.UiModel>

    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(
            getLocation,
            getDataStations,
            coroutinesTestRule.testDispatcher,
            coroutinesTestRule.testDispatcher
        )
    }

    @Test
    fun `observing LiveData launches location permission request`() {

        mainViewModel.model.observeForever(observer)

        verify(observer).onChanged(MainViewModel.UiModel.RequestLocationPermission)
    }

    @Test
    fun `after requesting the permission, loading is shown`() {

        val stations = listOf(mockedStation.copy(id = 4))

        runBlocking {

            whenever(getDataStations.invoke()).thenReturn(stations)
            whenever(getLocation.invoke()).thenReturn(mockedDefaultLocation)
            mainViewModel.model.observeForever(observer)

            mainViewModel.onCoarsePermissionRequest()
        }

        verify(observer).onChanged(MainViewModel.UiModel.Loading)
    }

    @Test
    fun `after requesting the permission, getDataStations is called`() {

        val stations = listOf(mockedStation.copy(id = 1))

        runBlocking {
            whenever(getDataStations.invoke()).thenReturn(stations)
            whenever(getLocation.invoke()).thenReturn(mockedDefaultLocation)
            mainViewModel.model.observeForever(observer)

            mainViewModel.onCoarsePermissionRequest()
        }

        verify(observer).onChanged(MainViewModel.UiModel.Content(stations))
    }

}
