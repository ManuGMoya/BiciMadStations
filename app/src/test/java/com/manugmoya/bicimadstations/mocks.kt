package com.manugmoya.bicimadstations

import com.manugmoya.data.repository.LocationRepository
import com.manugmoya.domain.GeometryDomain
import com.manugmoya.domain.LocationDomain
import com.manugmoya.domain.StationDomain

private val mockedGeometry = GeometryDomain(
    listOf(0.0, 1.1)
)

val mockedStation = StationDomain(
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

val mockedDefaultLocation = LocationDomain(
    LocationRepository.LATITUDE_MADRID_CENTER,
    LocationRepository.LONGITUDE_MADRID_CENTER
)
