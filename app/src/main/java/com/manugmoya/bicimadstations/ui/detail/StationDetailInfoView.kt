package com.manugmoya.bicimadstations.ui.detail

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.buildSpannedString
import com.manugmoya.bicimadstations.R
import com.manugmoya.bicimadstations.ui.common.appendInfo
import com.manugmoya.bicimadstations.model.Station


class StationDetailInfoView : AppCompatTextView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setStation(station: Station) = with(station ){
        text = buildSpannedString {

            appendInfo(context, R.string.address, address)

            val available = if (noAvailable == 0){
                context.applicationContext.getString(R.string.station_enabled)
            }else{
                context.applicationContext.getString(R.string.station_disabled)
            }
            appendInfo(context, R.string.available_station, available)

            appendInfo(context, R.string.total_bases, totalBases.toString())

            appendInfo(context, R.string.free_bases, freeBases.toString())

            appendInfo(context, R.string.bikes_in_bases, dockBikes.toString())

            appendInfo(context, R.string.reservations_number, reservationsCount.toString())

        }
    }



}