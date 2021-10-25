package com.example.freenowapp.domain

import com.example.freenowapp.remote.model.Vehicle
import com.example.freenowapp.repo.VehiclesRepository
import com.example.freenowapp.utils.ResponseStatus

class GetVehicles(private val vehicleRepository: VehiclesRepository) {
    suspend fun execute(
        p1Lat: Double,
        p1Lon: Double,
        p2Lat: Double,
        p2Lon: Double
    ): ResponseStatus<List<Vehicle>> {
        return try {
            val response = vehicleRepository.getVehicles(p1Lat, p1Lon, p2Lat, p2Lon)
            ResponseStatus.success(response.vehicles)
        } catch (e: Exception) {
            ResponseStatus.error(e.toString())
        }

    }
}