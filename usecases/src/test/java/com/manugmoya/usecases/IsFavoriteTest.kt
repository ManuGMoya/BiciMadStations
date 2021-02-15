package com.manugmoya.usecases

import com.manugmoya.data.repository.StationRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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
    fun `check is favorite returns null`() {

        runBlocking {
            // GIVEN
            val favorite = mockedFavorite.copy(id = 5)
            whenever(stationRepository.isFavorite(id = 5)).thenReturn(null)

            // WHEN
            val result = isFavorite.invoke(favorite.id)

            // THEN
            assertEquals(result, null)
        }
    }

    @Test
    fun `check is favorite returns the result from repository`() {

        runBlocking {
            // GIVEN
            val favorite = mockedFavorite.copy(id = 5)
            whenever(stationRepository.isFavorite(id = 5)).thenReturn(favorite)

            // WHEN
            val result = isFavorite.invoke(favorite.id)

            // THEN
            assertEquals(result, favorite)
        }

    }
}