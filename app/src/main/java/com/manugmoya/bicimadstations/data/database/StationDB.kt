package com.manugmoya.bicimadstations.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.manugmoya.bicimadstations.data.server.Geometry

@Entity
data class StationDB (
    @PrimaryKey val id: Long,
    var distanceTo: Float,
    val name: String,
    val light: Int,
    val number: String,
    val address: String,
    val activate: Long,
    val noAvailable: Int,
    val totalBases: Int,
    val dockBikes: Int,
    val freeBases: Int,
    val reservationsCount: Int,
    val geometry: Geometry
)


class Converters {
    @TypeConverter
    fun setGeometryToString(value: Geometry) : String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun getGeometryToString(value: String): Geometry {
        return Gson().fromJson(value, Geometry::class.java)
    }
}

@Entity
data class Favorite(
    @PrimaryKey val id: Long
)