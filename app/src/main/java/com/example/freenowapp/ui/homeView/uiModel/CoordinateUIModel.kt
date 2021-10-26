package com.example.freenowapp.ui.homeView.uiModel

import android.os.Parcelable
import com.example.freenowapp.remote.model.Coordinate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoordinateUIModel(val latitude: Double?, val longitude: Double?) : Parcelable

fun Coordinate.toUIModel(): CoordinateUIModel {
    return CoordinateUIModel(latitude, longitude)
}