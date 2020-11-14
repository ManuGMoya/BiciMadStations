package com.manugmoya.bicimadstations.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Token (
    val code: String?,
    val description: String?,
    val datetime: String?,
    val data: List<Data>?
)


data class Data (
    val nameApp: String?,
    val levelApp: Long?,
    val updatedAt: String?,
    val userName: String?,
    val lastUpdate: LastUpdate?,
    val idUser: String?,
    val priv: String?,
    val tokenSECExpiration: Long?,
    val email: String?,
    val tokenDTEExpiration: LastUpdate?,
    val flagAdvise: Boolean?,
    val accessToken: String?,
    val apiCounter: APICounter?,
    val username: String?
)


data class APICounter (
    val current: Long?,
    val dailyUse: Long?,
    val owner: Long?,
    val licenceUse: String?,
    val aboutUses: String?
)


data class LastUpdate (
    val date: Long?
)

//////////////////////////////

data class Stations (
    val code: String,
    val description: String,
    val datetime: String,
    val data: List<Station>
)

@Parcelize
data class Station (
    val id: Long,
    val name: String,
    val light: Long,
    val number: String,
    val address: String,
    val activate: Long,
    val noAvailable: Long,
    val totalBases: Long,
    val dockBikes: Long,
    val freeBases: Long,
    val reservationsCount: Long,
    val geometry: Geometry
): Parcelable

@Parcelize
data class Geometry (
    val type: Type,
    val coordinates: List<Double>
): Parcelable


enum class Type {
    Point
}