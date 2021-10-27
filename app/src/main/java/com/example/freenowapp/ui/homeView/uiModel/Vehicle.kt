package com.example.freenowapp.ui.homeView.uiModel

import android.os.Parcelable
import com.example.freenowapp.remote.model.FleetType
import com.example.freenowapp.remote.model.RemoteVehicle
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vehicle(
    val id: Int?,
    val coordinate: Coordinate?,
    val fleetType: FleetType?,
    val heading: Double?
): Parcelable

fun RemoteVehicle.toVehicleUIModel() = Vehicle(
    id,
    remoteCoordinate?.toUIModel(),
    fleetType,
    heading
)