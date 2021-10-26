package com.example.freenowapp.domain

import com.example.freenowapp.*
import com.example.freenowapp.repo.VehiclesRepository
import com.example.freenowapp.utils.ResponseStatus
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class GetVehiclesTest {

    private lateinit var getVehicles: GetVehicles

    @Mock
    private lateinit var vehiclesRepository: VehiclesRepository


    @BeforeEach
    fun setUp() {
        getVehicles = GetVehicles(vehiclesRepository)
    }


    @Test
    fun `when get vehicle then vehicle list is returned with list`() {
        runBlocking {
            Mockito.`when`(
                vehiclesRepository.getVehicles(
                    TEST_DEF_P1LAT,
                    TEST_DEF_P1LONG,
                    TEST_DEF_P2LAT,
                    TEST_DEF_P2LONG
                )
            ).thenReturn(fakeVehiclesResponse)
            val actualValue =
                getVehicles.execute(
                    TEST_DEF_P1LAT,
                    TEST_DEF_P1LONG,
                    TEST_DEF_P2LAT,
                    TEST_DEF_P2LONG
                )
            assertEquals(
                actualValue, ResponseStatus.success(fakeVehiclesResponse.vehicles)
            )
        }
    }


}