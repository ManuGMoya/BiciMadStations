package com.manugmoya.bicimadstations.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.manugmoya.bicimadstations.data.toStationRoom
import com.manugmoya.bicimadstations.mockedStation
import com.manugmoya.domain.FavoriteDomain
import com.manugmoya.usecases.DeleteFavorite
import com.manugmoya.usecases.FindStationById
import com.manugmoya.usecases.InsertFavorite
import com.manugmoya.usecases.IsFavorite
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findStationById: FindStationById

    @Mock
    lateinit var insertFavorite: InsertFavorite

    @Mock
    lateinit var deleteFavorite: DeleteFavorite

    @Mock
    lateinit var isFavorite: IsFavorite

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    @Mock
    lateinit var observerFavorite: Observer<Boolean>

    lateinit var detailViewModel: DetailViewModel


    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(
            stationId = 1,
            findStationById,
            insertFavorite,
            deleteFavorite,
            isFavorite,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `observing LiveData finds the movie`() {

        val station = mockedStation.copy(id = 1)

        runBlocking {
            whenever(findStationById.invoke(1)).thenReturn(station)
            detailViewModel.model.observeForever(observer)
        }

        verify(observer).onChanged(DetailViewModel.UiModel(station.toStationRoom()))
    }

    @Test
    fun `when favorite is clicked, isFavorite use case is invoked`() {

        runBlocking {
            detailViewModel.favorite.observeForever(observerFavorite)

            detailViewModel.onFavoriteClicked()

            verify(isFavorite).invoke(1)
        }

        verify(observerFavorite).onChanged(true)
    }

    @Test
    fun `when favorite is clicked, deleteFavorite use case is invoked`() {

        runBlocking {
            whenever(isFavorite.invoke(1)).thenReturn(FavoriteDomain(1))
            detailViewModel.favorite.observeForever(observerFavorite)

            detailViewModel.onFavoriteClicked()

            verify(deleteFavorite).invoke(1)
        }

        verify(observerFavorite).onChanged(false)
    }

    @Test
    fun `when favorite is clicked, insertFavorite use case is invoked`() {

        runBlocking {
            whenever(isFavorite.invoke(1)).thenReturn(null)
            detailViewModel.favorite.observeForever(observerFavorite)

            detailViewModel.onFavoriteClicked()

            verify(insertFavorite).invoke(1)
        }

        verify(observerFavorite).onChanged(true)
    }

}
