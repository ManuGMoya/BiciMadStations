package com.manugmoya.domain

data class StationDomain (
    val id: Long,
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
    val geometry: GeometryDomain
)

data class GeometryDomain(
    val coordinates: List<Double>
)