package com.example.freenowapp.repo

import com.example.freenowapp.*
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
                    TEST_DEF_P1LAT,
                    TEST_DEF_P1LONG,
                    TEST_DEF_P2LAT,
                    TEST_DEF_P2LONG
                )
            ).thenReturn(
                fakeVehiclesResponse
            )
            val actualValue =
                vehiclesRepository.getVehicles(
                    TEST_DEF_P1LAT,
                    TEST_DEF_P1LONG,
                    TEST_DEF_P2LAT,
                    TEST_DEF_P2LONG
                )
            Assertions.assertEquals(
                actualValue, fakeVehiclesResponse
            )
        }
    }

}