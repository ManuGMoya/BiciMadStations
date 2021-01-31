package com.manugmoya.data.repository

import com.manugmoya.data.source.LocationDataSource
import com.manugmoya.domain.LocationDomain
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
class LocationRepositoryTest {

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    lateinit var locationRepository: LocationRepository

    @Before
    fun setUp() {
        locationRepository = LocationRepository(locationDataSource, permissionChecker)
    }

    @Test
    fun `returns default location when fine location not granted`() {

        runBlocking {
            // GIVEN
            whenever(permissionChecker.check(PermissionChecker.Permission.FINE_LOCATION)).thenReturn(
                false
            )

            // WHEN
            val location = locationRepository.getLocation()

            // THEN
            assertEquals(mockedDefaultLocation, location)
        }
    }

    @Test
    fun `getLocation calls location data source`() {

        runBlocking {
            // GIVEN
            whenever(permissionChecker.check(PermissionChecker.Permission.FINE_LOCATION)).thenReturn(
                true
            )

            // WHEN
            locationRepository.getLocation()

            // THEN
            verify(locationDataSource).getLocation()
        }
    }


    private val mockedDefaultLocation = LocationDomain(
        LocationRepository.LATITUDE_MADRID_CENTER,
        LocationRepository.LONGITUDE_MADRID_CENTER
    )

}
