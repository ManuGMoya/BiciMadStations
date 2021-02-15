package com.manugmoya.bicimadstations.data

import android.location.Location
import com.manugmoya.bicimadstations.data.server.Geometry
import com.manugmoya.bicimadstations.data.server.Station
import com.manugmoya.domain.FavoriteDomain
import com.manugmoya.domain.GeometryDomain
import com.manugmoya.domain.LocationDomain
import com.manugmoya.domain.StationDomain
import com.manugmoya.bicimadstations.data.database.Favorite as FavoriteRoom
import com.manugmoya.bicimadstations.data.database.StationDB as StationRoom

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

// LOCATION
fun Location.toLocationDomain(): LocationDomain =
    LocationDomain(
        this.latitude,
        this.longitude
    )

fun LocationDomain.toLocation(): Location {
    val location = Location("")
    location.latitude = this.latitude
    location.longitude = this.longitude

    return location
}
