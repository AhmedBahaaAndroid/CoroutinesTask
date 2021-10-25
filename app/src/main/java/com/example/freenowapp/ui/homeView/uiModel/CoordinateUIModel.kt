package com.example.freenowapp.ui.homeView.uiModel

import com.example.freenowapp.remote.model.Vehicle

fun Vehicle.toVehicleUIModel() = VehicleUIModel(
    id,
    CoordinateUIModel(coordinate?.latitude, coordinate?.longitude),
    fleetType,
    heading
)