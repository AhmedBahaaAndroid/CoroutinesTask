package com.example.freenowapp.ui.homeView.uiModel

import com.example.freenowapp.remote.model.FleetType
import com.example.freenowapp.remote.model.Vehicle

data class VehicleUIModel(
    val id: Int?,
    val coordinate: CoordinateUIModel?,
    val fleetType: FleetType?,
    val heading: Double?
)

fun Vehicle.toVehicleUIModel() = VehicleUIModel(
    id,
    coordinate?.toUIModel(),
    fleetType,
    heading
)