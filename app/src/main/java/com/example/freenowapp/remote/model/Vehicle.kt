package com.example.freenowapp.remote.model

import com.example.freenowapp.ui.homeView.uiModel.CoordinateUIModel
import com.example.freenowapp.ui.homeView.uiModel.VehicleUIModel
import com.google.gson.annotations.SerializedName

data class Vehicle(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("coordinate") val coordinate: Coordinate? = null,
    @SerializedName("fleetType") val fleetType: FleetType? = null,
    @SerializedName("heading") val heading: Double? = null
)
