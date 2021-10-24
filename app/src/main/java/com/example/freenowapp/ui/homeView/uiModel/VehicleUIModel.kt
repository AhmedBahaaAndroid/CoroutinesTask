package com.example.freenowapp.ui.homeView.uiModel

import com.example.freenowapp.remote.model.FleetType

data class VehicleUIModel(
    val id: Int?,
    val coordinate: CoordinateUIModel?,
    val fleetType: FleetType?,
    val heading: Double?
)

data class CoordinateUIModel(val latitude: Double?, val longitude: Double?)