package com.manugmoya.bicimadstations.ui.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manugmoya.bicimadstations.StationApp
import com.manugmoya.domain.StationDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun SpannableStringBuilder.appendInfo(context: Context, stringRes: Int, value: String) {
    bold {
        append(context.resources.getString(stringRes))
        append(": ")
    }
    appendLine(value)
}

fun ViewGroup.inflate(layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

inline fun <reified T : Activity> Context.intentFor(body: Intent.() -> Unit): Intent =
    Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.startActivity(body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body))
}

suspend fun List<StationDomain>.orderListByLocation(
    location: Location,
    dispatcher: CoroutineDispatcher
): List<StationDomain> {
    return withContext(dispatcher) {
        forEach {
            val stationLocation = Location("").apply {
                longitude = it.geometry.coordinates[0]
                latitude = it.geometry.coordinates[1]
            }
            it.distanceTo = location.distanceTo(stationLocation)
        }
        sortedBy { it.distanceTo }
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this, vmFactory)[T::class.java]
}

val Context.app: StationApp
    get() = applicationContext as StationApp

fun Context.isGPSEnabled(): Boolean {
    val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}
