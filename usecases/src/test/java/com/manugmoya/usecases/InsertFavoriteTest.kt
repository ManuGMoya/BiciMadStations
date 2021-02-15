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
class InsertFavoriteTest {

    @Mock
    lateinit var stationRepository: StationRepository

    lateinit var insertFavorite: InsertFavorite

    @Before
    fun setUp() {
        insertFavorite = InsertFavorite(stationRepository)
    }

    @Test
    fun `invoke calls station repository`() {

        runBlocking {
            // GIVEN
            val station = mockedStation.copy(id = 8)

            // WHEN
            insertFavorite.invoke(station.id)

            // THEN
            verify(stationRepository).insertFav(station.id)
        }
    }
}