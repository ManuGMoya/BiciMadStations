package com.manugmoya.data.source

import com.manugmoya.domain.LocationDomain

interface LocationDataSource{
    suspend fun getLocation(): LocationDomain?
}