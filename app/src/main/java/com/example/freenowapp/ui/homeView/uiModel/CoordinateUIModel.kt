package com.example.freenowapp.ui.homeView.uiModel

import com.example.freenowapp.remote.model.Coordinate


data class CoordinateUIModel(val latitude: Double?, val longitude: Double?)

fun Coordinate.toUIModel(): CoordinateUIModel {
    return CoordinateUIModel(latitude, longitude)
}