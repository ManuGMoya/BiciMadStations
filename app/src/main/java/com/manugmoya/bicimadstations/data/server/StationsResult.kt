package com.manugmoya.bicimadstations.data.server

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Token(
    val code: String?,
    val description: String?,
    val datetime: String?,
    val data: List<Data>?
)


data class Data(
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


data class APICounter(
    val current: Long?,
    val dailyUse: Long?,
    val owner: Long?,
    val licenceUse: String?,
    val aboutUses: String?
)


data class LastUpdate(
    val date: Long?
)

//////////////////////////////
@Parcelize
data class Stations(
    val code: String,
    val description: String,
    val datetime: String,
    val data: List<Station>
) : Parcelable

@Parcelize
data class Station(
    var distanceTo: Float,
    val id: Long,
    val name: String,
    val light: Int,
    val number: String,
    val address: String,
    val activate: Long,
    @SerializedName("no_available") val noAvailable: Int,
    @SerializedName("total_bases") val totalBases: Int,
    @SerializedName("dock_bikes") val dockBikes: Int,
    @SerializedName("free_bases") val freeBases: Int,
    @SerializedName("reservations_count") val reservationsCount: Int,
    val geometry: Geometry
) : Parcelable

@Parcelize
data class Geometry(
    val coordinates: List<Double>
) : Parcelable

