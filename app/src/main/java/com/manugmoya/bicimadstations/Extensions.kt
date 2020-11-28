package com.manugmoya.bicimadstations

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import com.manugmoya.bicimadstations.model.Station
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun SpannableStringBuilder.appendInfo(context: Context,stringRes: Int, value: String) {
    bold {
        append(context.resources.getString(stringRes))
        append(": ")
    }
    appendLine(value)
}

fun ViewGroup.inflate(layoutRes: Int, attachToRoot: Boolean = true) : View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

inline fun <reified T : Activity> Context.intentFor(body: Intent.() -> Unit): Intent =
    Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.startActivity(body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body))
}

suspend fun List<Station>.orderListByLocation(location: Location) : List<Station> {
    return withContext(Dispatchers.Default) {
        this@orderListByLocation.forEach {
            val stationLocation = Location("").apply {
                longitude = it.geometry.coordinates[0]
                latitude = it.geometry.coordinates[1]
            }
            it.distanceTo = location.distanceTo(stationLocation)
        }
        this@orderListByLocation.sortedBy { it.distanceTo }
    }
}