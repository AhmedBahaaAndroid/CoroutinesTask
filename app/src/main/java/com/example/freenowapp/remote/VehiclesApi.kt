package com.example.freenowapp.remote

import com.example.freenowapp.remote.model.RemoteVehicleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VehiclesApi {
    @GET("/")
    suspend fun getVehiclesInBounds(
        @Query("p1Lat") p1Lat: Double,
        @Query("p1Lon") p1Lon: Double,
        @Query("p2Lat") p2Lat: Double,
        @Query("p2Lon") p2Lon: Double
    ): RemoteVehicleResponse

}