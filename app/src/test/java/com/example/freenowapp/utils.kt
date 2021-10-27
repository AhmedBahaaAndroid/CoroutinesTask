package com.example.freenowapp

import com.example.freenowapp.remote.model.RemoteCoordinate
import com.example.freenowapp.remote.model.FleetType
import com.example.freenowapp.remote.model.RemoteVehicleResponse
import com.example.freenowapp.remote.model.RemoteVehicle

val fakeVehiclesResponse = RemoteVehicleResponse(
    listOf(
        RemoteVehicle(
            1,
            RemoteCoordinate(11.00, 11.00),
            FleetType.POOLING
        ),
        RemoteVehicle(
            2,
            RemoteCoordinate(11.00, 11.00),
            FleetType.TAXI
        )
    )
)

const val TEST_DEF_P1LAT = 53.694865
const val TEST_DEF_P1LONG = 53.694865
const val TEST_DEF_P2LAT = 53.394655
const val TEST_DEF_P2LONG = 10.099891