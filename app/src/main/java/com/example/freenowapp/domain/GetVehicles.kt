package com.example.freenowapp.domain

import com.example.freenowapp.repo.VehiclesRepository

class GetVehicles(val vehicleRepository: VehiclesRepository) {
    suspend fun execute(p1Lat: Double, p1Lon: Double, p2Lat: Double, p2Lon: Double) =
        vehicleRepository.getVehicles(p1Lat, p1Lon, p2Lat, p2Lon)
}