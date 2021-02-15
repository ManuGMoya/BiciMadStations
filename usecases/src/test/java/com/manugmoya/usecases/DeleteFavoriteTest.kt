package com.manugmoya.usecases

import com.manugmoya.data.repository.StationRepository
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeleteFavoriteTest {

    @Mock
    lateinit var stationRepository: StationRepository


    lateinit var deleteFavorite: DeleteFavorite

    @Before
    fun setUp() {
        deleteFavorite = DeleteFavorite(stationRepository)
    }

    @Test
    fun `invoke calls station repository`() {

        runBlocking {
            // GIVEN
            val station = mockedStation.copy(id = 5)

            // WHEN
            deleteFavorite.invoke(station.id)

            // THEN
            verify(stationRepository).deleteFav(station.id)
        }
    }
}