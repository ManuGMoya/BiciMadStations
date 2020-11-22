package com.manugmoya.bicimadstations

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.core.text.bold

fun SpannableStringBuilder.appendInfo(context: Context,stringRes: Int, value: String) {
    bold {
        append(context.resources.getString(stringRes))
        append(": ")
    }
    appendLine(value)
}