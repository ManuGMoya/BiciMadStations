package com.manugmoya.usecases

import com.manugmoya.data.repository.StationRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FindStationByIdTest {

    @Mock
    lateinit var stationRepository: StationRepository

    lateinit var findStationById: FindStationById

    @Before
    fun setUp() {
        findStationById = FindStationById(stationRepository)
    }

    @Test
    fun `invoke calls station repository`(){

        runBlocking {
            // GIVEN
            val station = mockedStation.copy(id = 4)
            whenever(stationRepository.findById(4)).thenReturn(station)
            // WHEN
            val result = findStationById.invoke(4)

            // THEN
            assertEquals(station, result)
        }

    }
}