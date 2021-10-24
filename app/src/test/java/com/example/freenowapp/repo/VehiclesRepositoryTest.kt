package com.example.freenowapp.repo

import com.example.freenowapp.fakeVehiclesResponse
import com.example.freenowapp.remote.VehiclesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class VehiclesRepositoryTest {

    private lateinit var vehiclesRepository: VehiclesRepository

    @Mock
    private lateinit var vehiclesApi: VehiclesApi


    @BeforeEach
    fun setUp() {
        vehiclesRepository = VehiclesRepository(vehiclesApi)
    }

    @Test
    fun `when get vehicle then vehicle list is returned with list`() {
        runBlocking {
            Mockito.`when`(
                vehiclesApi.getVehiclesInBounds(
                    53.694865, 9.757589, 53.394655, 10.099891
                )
            ).thenReturn(
                fakeVehiclesResponse
            )
            val actualValue =
                vehiclesRepository.getVehicles(53.694865, 9.757589, 53.394655, 10.099891)
            Assertions.assertEquals(
                actualValue, fakeVehiclesResponse
            )
        }
    }

}