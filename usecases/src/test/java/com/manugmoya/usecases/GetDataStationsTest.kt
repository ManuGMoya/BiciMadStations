package com.manugmoya.usecases

import com.manugmoya.data.repository.StationRepository
import com.manugmoya.domain.StationDomain
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetDataStationsTest {

    @Mock
    lateinit var stationRepository: StationRepository

    lateinit var getDataStations: GetDataStations

    @Before
    fun setUp() {
        getDataStations = GetDataStations(stationRepository)
    }

    @Test
    fun `invoke calls station repository`() {

        runBlocking {
            // GIVEN
            val stationList = listOf<StationDomain>(mockedStation.copy(id = 6))
            whenever(stationRepository.getDataStations()).thenReturn(stationList)

            // WHEN
            val result = getDataStations.invoke()

            // THEN
            assertEquals(stationList, result)
        }
    }
}