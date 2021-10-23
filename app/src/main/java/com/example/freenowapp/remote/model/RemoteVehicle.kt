package com.example.freenowapp.remote.model

import com.google.gson.annotations.SerializedName


data class RemoteVehicleResponse(@SerializedName("poiList") val vehicles: List<RemoteVehicle>)

data class RemoteVehicle(
    @SerializedName("coordinate") val coordinate: Coordinate? = null,
    @SerializedName("fleetType") val fleetType: FleetType? = null,
    @SerializedName("heading") val heading: Double? = null,
    @SerializedName("id") val id: Int? = null
)

enum class FleetType {
    POOLING, TAXI
}

data class Coordinate(
    @SerializedName("latitude") val latitude: Double? = null,
    @SerializedName("longitude") val longitude: Double? = null
)