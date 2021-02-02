package com.manugmoya.data.repository

import com.manugmoya.data.source.LocalDataSource
import com.manugmoya.data.source.RemoteDatasource
import com.manugmoya.domain.FavoriteDomain
import com.manugmoya.domain.GeometryDomain
import com.manugmoya.domain.StationDomain
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
class StationRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDatasource: RemoteDatasource

    lateinit var stationRepository: StationRepository

    // FAKES
    private val email = "fakeEmail"

    private val password = "fakePassword"

    private val apiKey = "fakeApiKey"


    @Before
    fun setUp() {
        stationRepository = StationRepository(localDataSource, remoteDatasource, email, password)
    }


    @Test
    fun `findById calls local data source and remote data source`() {
        runBlocking {
            // GIVEN
            val station = mockedStation.copy(id = 4)
            whenever(localDataSource.findById(4)).thenReturn(station)

            // WHEN
            val result = stationRepository.findById(4)

            // THEN
            assertEquals(station, result)
        }
    }

    @Test
    fun `insertFav calls local data source`() {
        runBlocking {
            // GIVEN
            val favoriteId = 5L
            val favorite = mockedFavorite

            // WHEN
            stationRepository.insertFav(favoriteId)

            // THEN
            verify(localDataSource).insertFav(favorite)
        }
    }

    @Test
    fun `deleteFav calls local data source`() {
        runBlocking {
            // GIVEN
            val favoriteId = 5L
            val favorite = mockedFavorite

            // WHEN
            stationRepository.deleteFav(favoriteId)

            // THEN
            verify(localDataSource).deleteFav(favorite)
        }
    }

    @Test
    fun `isFavorite calls local data source`() {
        runBlocking {
            // GIVEN
            val favoriteId = 5L

            // WHEN
            stationRepository.isFavorite(favoriteId)

            // THEN
            verify(localDataSource).isFavorite(favoriteId)
        }
    }

    @Test
    fun `getDataStations calls local data source`() {
        runBlocking {
            // GIVEN
            val station = mockedStation.copy(id = 3)
            whenever(localDataSource.getStations()).thenReturn(listOf(station))

            // WHEN
            val stationList = stationRepository.getDataStations()

            // THEN
            assertEquals(listOf(station), stationList)
        }
    }

    @Test
    fun `getDataStations calls getToken of remote data source`() {
        runBlocking {
            // GIVEN
            val station = mockedStation.copy(id = 3)
            whenever(localDataSource.getStations()).thenReturn(listOf(station))

            // WHEN
            stationRepository.getDataStations()

            // THEN
            verify(remoteDatasource).getToken(email, password)
        }
    }

    @Test
    fun `getDataStations calls getDataStation of remote data source`() {

        runBlocking {
            // GIVEN
            whenever(remoteDatasource.getToken(email, password)).thenReturn(apiKey)

            // WHEN
            stationRepository.getDataStations()

            // THEN
            verify(remoteDatasource).getDataStations(apiKey)
        }
    }

    @Test
    fun `getDataStations calls insertStations of local data source`() {

        runBlocking {
            // GIVEN
            val stationList = listOf(mockedStation)
            whenever(remoteDatasource.getToken(email, password)).thenReturn(apiKey)
            whenever(remoteDatasource.getDataStations(apiKey)).thenReturn(stationList)

            // WHEN
            stationRepository.getDataStations()

            // THEN
            verify(localDataSource).insertStations(stationList)
        }
    }

    @Test
    fun `getDataStations calls getStations of local data source`() {

        runBlocking {
            // GIVEN
            val stationList = listOf(mockedStation)
            whenever(remoteDatasource.getToken(email, password)).thenReturn(apiKey)
            whenever(remoteDatasource.getDataStations(apiKey)).thenReturn(stationList)
            whenever(localDataSource.getStations()).thenReturn(stationList)

            // WHEN
            stationRepository.getDataStations()
            val result = localDataSource.getStations()

            // THEN
            assertEquals(stationList, result)
        }
    }


    private val mockedGeometry = GeometryDomain(
        listOf(0.0, 1.1)
    )

    private val mockedStation = StationDomain(
        0,
        0.0F,
        "Name",
        0,
        "1",
        "Address",
        0,
        0,
        0,
        0,
        0,
        0,
        mockedGeometry
    )

    private val mockedFavorite = FavoriteDomain(5)
}