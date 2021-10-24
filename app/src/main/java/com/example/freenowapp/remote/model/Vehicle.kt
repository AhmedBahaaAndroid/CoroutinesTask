package com.example.freenowapp.remote.model

import com.example.freenowapp.ui.homeView.uiModel.CoordinateUIModel
import com.example.freenowapp.ui.homeView.uiModel.VehicleUIModel
import com.google.gson.annotations.SerializedName


data class RemoteVehicleResponse(@SerializedName("poiList") val vehicles: List<Vehicle>)

data class Vehicle(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("coordinate") val coordinate: Coordinate? = null,
    @SerializedName("fleetType") val fleetType: FleetType? = null,
    @SerializedName("heading") val heading: Double? = null
)

enum class FleetType {
    POOLING, TAXI
}

data class Coordinate(
    @SerializedName("latitude") val latitude: Double? = null,
    @SerializedName("longitude") val longitude: Double? = null
)

fun Vehicle.toVehicleUIModel() = VehicleUIModel(
    id,
    CoordinateUIModel(coordinate?.latitude, coordinate?.longitude),
    fleetType,
    heading
)
