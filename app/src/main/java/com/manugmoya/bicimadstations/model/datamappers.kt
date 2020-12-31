package com.manugmoya.bicimadstations.model

import com.manugmoya.bicimadstations.model.server.Geometry
import com.manugmoya.bicimadstations.model.server.Station
import com.manugmoya.domain.FavoriteDomain
import com.manugmoya.domain.GeometryDomain
import com.manugmoya.domain.StationDomain
import com.manugmoya.bicimadstations.model.database.Favorite as FavoriteRoom
import com.manugmoya.bicimadstations.model.database.StationDB as StationRoom

// ROOM
fun StationDomain.toStationRoom() = StationRoom(
    id,
    distanceTo,
    name,
    light,
    number,
    address,
    activate,
    noAvailable,
    totalBases,
    dockBikes,
    freeBases,
    reservationsCount,
    geometry.toGeometryRoom()
)

fun GeometryDomain.toGeometryRoom() = Geometry(
    this.coordinates
)

fun StationRoom.toStationDomain() = StationDomain(
    id,
    distanceTo,
    name,
    light,
    number,
    address,
    activate,
    noAvailable,
    totalBases,
    dockBikes,
    freeBases,
    reservationsCount,
    geometry.toGeometryDomain()
)

fun Geometry.toGeometryDomain() = GeometryDomain(
    this.coordinates
)

fun FavoriteDomain.toFavoriteRoom() = FavoriteRoom(
    this.id
)

fun FavoriteRoom.toFavoriteDomain() = FavoriteDomain(
    this.id
)

// SERVER
fun Station.toStationDomain() = StationDomain(
    id,
    distanceTo,
    name,
    light,
    number,
    address,
    activate,
    noAvailable,
    totalBases,
    dockBikes,
    freeBases,
    reservationsCount,
    geometry.toGeometryDomain()
)


