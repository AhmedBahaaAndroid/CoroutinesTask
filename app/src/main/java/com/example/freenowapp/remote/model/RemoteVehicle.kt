package com.example.freenowapp.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteVehicle(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("coordinate") val remoteCoordinate: RemoteCoordinate? = null,
    @SerializedName("fleetType") val fleetType: FleetType? = null,
    @SerializedName("heading") val heading: Double? = null
)
