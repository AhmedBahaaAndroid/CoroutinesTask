package com.example.freenowapp.ui.homeView.uiModel

import android.os.Parcelable
import com.example.freenowapp.remote.model.FleetType
import com.example.freenowapp.remote.model.Vehicle
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VehicleUIModel(
    val id: Int?,
    val coordinate: CoordinateUIModel?,
    val fleetType: FleetType?,
    val heading: Double?
): Parcelable

fun Vehicle.toVehicleUIModel() = VehicleUIModel(
    id,
    coordinate?.toUIModel(),
    fleetType,
    heading
)