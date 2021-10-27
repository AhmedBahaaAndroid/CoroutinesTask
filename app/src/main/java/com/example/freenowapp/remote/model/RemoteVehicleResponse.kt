package com.example.freenowapp.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteVehicleResponse(@SerializedName("poiList") val remoteVehicles: List<RemoteVehicle>)
