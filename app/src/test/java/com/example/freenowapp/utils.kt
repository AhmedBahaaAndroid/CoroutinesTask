package com.example.freenowapp

import com.example.freenowapp.remote.model.Coordinate
import com.example.freenowapp.remote.model.FleetType
import com.example.freenowapp.remote.model.RemoteVehicleResponse
import com.example.freenowapp.remote.model.Vehicle

val fakeVehiclesResponse = RemoteVehicleResponse(
    listOf(
        Vehicle(
            1,
            Coordinate(11.00, 11.00),
            FleetType.POOLING
        ),
        Vehicle(
            2,
            Coordinate(11.00, 11.00),
            FleetType.TAXI
        )
    )
)