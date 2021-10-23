package com.example.freenowapp.repo

import com.example.freenowapp.remote.VehiclesApi

class VehiclesRepository(val vehiclesService: VehiclesApi) {

    suspend fun getVehicles(p1Lat: Double, p1Lon: Double, p2Lat: Double, p2Lon: Double) =
        vehiclesService.getVehiclesInBounds(p1Lat, p1Lon, p2Lat, p2Lon)
}