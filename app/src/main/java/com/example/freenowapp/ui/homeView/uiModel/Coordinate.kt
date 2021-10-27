package com.example.freenowapp.ui.homeView.uiModel

import android.os.Parcelable
import com.example.freenowapp.remote.model.RemoteCoordinate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinate(val latitude: Double?, val longitude: Double?) : Parcelable

fun RemoteCoordinate.toUIModel(): Coordinate {
    return Coordinate(latitude, longitude)
}