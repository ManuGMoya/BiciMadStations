package com.manugmoya.bicimadstations.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FakeData (
    val title: String,
    val address: String
): Parcelable