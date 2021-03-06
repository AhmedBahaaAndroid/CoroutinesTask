package com.example.freenowapp.domain

import com.example.freenowapp.remote.model.RemoteVehicle
import com.example.freenowapp.repo.VehiclesRepository
import com.example.freenowapp.utils.ResponseStatus
import com.example.freenowapp.errorHandling.mapThrowable

class GetVehicles(private val vehicleRepository: VehiclesRepository) {
    suspend fun execute(
        p1Lat: Double,
        p1Lon: Double,
        p2Lat: Double,
        p2Lon: Double
    ): ResponseStatus<List<RemoteVehicle>> {
        return try {
            val response = vehicleRepository.getVehicles(p1Lat, p1Lon, p2Lat, p2Lon)
            ResponseStatus.success(response.remoteVehicles)
        } catch (e: Throwable) {
            ResponseStatus.error(mapThrowable(e))
        }
    }
}