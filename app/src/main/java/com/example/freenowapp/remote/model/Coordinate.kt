package com.example.freenowapp.remote.model

import com.google.gson.annotations.SerializedName

data class Coordinate(
    @SerializedName("latitude") val latitude: Double? = null,
    @SerializedName("longitude") val longitude: Double? = null
)