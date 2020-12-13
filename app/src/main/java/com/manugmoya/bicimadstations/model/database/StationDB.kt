package com.manugmoya.bicimadstations.model.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson


@Entity
data class StationDB (
    @PrimaryKey(autoGenerate = true) val id: Long,
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
    val geometry: Geometry,
    val favorite: Boolean
)

@Entity
data class Geometry(
    val coordinates: List<Double>
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