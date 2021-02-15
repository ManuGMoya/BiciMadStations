package com.manugmoya.bicimadstations.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.manugmoya.bicimadstations.FakeLocalDataSource
import com.manugmoya.bicimadstations.data.toStationRoom
import com.manugmoya.bicimadstations.defaultFakeStations
import com.manugmoya.bicimadstations.initMockedDi
import com.manugmoya.bicimadstations.mockedStation
import com.manugmoya.data.source.LocalDataSource
import com.manugmoya.domain.FavoriteDomain
import com.manugmoya.usecases.DeleteFavorite
import com.manugmoya.usecases.FindStationById
import com.manugmoya.usecases.InsertFavorite
import com.manugmoya.usecases.IsFavorite
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    @Mock
    lateinit var observerFavorite: Observer<Boolean>

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var fakeLocalDataSource: FakeLocalDataSource

    @Before
    fun setup() {
        val vmModule = module {
            factory { (id: Long) -> DetailViewModel(id, get(), get(), get(), get(), get()) }
            factory { FindStationById(get()) }
            factory { InsertFavorite(get()) }
            factory { DeleteFavorite(get()) }
            factory { IsFavorite(get()) }
        }

        initMockedDi(vmModule)
        detailViewModel = get { parametersOf(1) }

        fakeLocalDataSource = get<LocalDataSource>() as FakeLocalDataSource
        fakeLocalDataSource.stationList = defaultFakeStations
    }

    @Test
    fun `observing LiveData finds the station`() {
        detailViewModel.model.observeForever(observer)

        verify(observer).onChanged(
            DetailViewModel.UiModel(
                mockedStation.copy(1).toStationRoom()
            )
        )

    }

    @Test
    fun `favorite is inserted in local data source`() {
        detailViewModel.favorite.observeForever(observerFavorite)

        detailViewModel.onFavoriteClicked()

        Assert.assertTrue(fakeLocalDataSource.favList.contains(FavoriteDomain(1)))
        verify(observerFavorite).onChanged(true)
    }

    @Test
    fun `favorite is deleted from local data source`() {
        detailViewModel.favorite.observeForever(observerFavorite)
        fakeLocalDataSource.favList.add(FavoriteDomain(1))

        detailViewModel.onFavoriteClicked()

        Assert.assertFalse(fakeLocalDataSource.favList.contains(FavoriteDomain(1)))
        verify(observerFavorite).onChanged(false)
    }
}
