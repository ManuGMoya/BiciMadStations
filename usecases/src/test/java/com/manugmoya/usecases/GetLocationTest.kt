package com.manugmoya.usecases

import com.manugmoya.data.repository.LocationRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetLocationTest {

    @Mock
    lateinit var locationRepository: LocationRepository

    lateinit var getLocation: GetLocation

    @Before
    fun setUp() {
        getLocation = GetLocation(locationRepository)
    }

    @Test
    fun `invoke calls location repository`() {

        runBlocking {

            // GIVEN
            getLocation.invoke()

            // THEN
            verify(locationRepository).getLocation()
        }
    }

    @Test
    fun `get default location`() {

        runBlocking {
            // WHEN
            val location = mockedDefaultLocation
            whenever(locationRepository.getLocation()).thenReturn(location)

            // GIVEN
            val result = getLocation.invoke()

            // THEN
            Assert.assertEquals(result, location)
        }
    }

}