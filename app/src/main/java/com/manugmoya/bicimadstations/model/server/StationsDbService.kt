package com.manugmoya.bicimadstations.model.server

import retrofit2.http.GET
import retrofit2.http.Header

interface StationsDbService {

    @GET("mobilitylabs/user/login/")
    suspend fun getToken(
        @Header("email") email: String,
        @Header("password") pwd: String,
    ): Token

    @GET("transport/bicimad/stations/")
    suspend fun getStation(
        @Header("accessToken") token: String
    ): Stations
}
