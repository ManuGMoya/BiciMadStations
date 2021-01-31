package com.manugmoya.usecases

import com.manugmoya.data.repository.StationRepository
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class IsFavoriteTest {

    @Mock
    lateinit var stationRepository: StationRepository

    lateinit var isFavorite: IsFavorite

    @Before
    fun setUp() {
        isFavorite = IsFavorite(stationRepository)
    }

    @Test
    fun `invoke calls station repository`() {

        runBlocking {
            // GIVEN
            val favorite = mockedFavorite

            // WHEN
            isFavorite.invoke(favorite.id)

            // THEN
            verify(stationRepository).isFavorite(favorite.id)
        }
    }

    @Test
    fun `check is favorite`() {
        runBlocking {
            // GIVEN
            val favorite = mockedFavorite.copy(id = 5)

            // WHEN
            val result = isFavorite.invoke(favorite.id)

            // THEN
            assertEquals(result, null)
        }
    }
}