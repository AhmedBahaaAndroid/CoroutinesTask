package com.example.freenowapp.ui.homeView.viewModel

import com.example.freenowapp.InstantExecutorExtension
import com.example.freenowapp.domain.GetVehicles
import com.example.freenowapp.fakeVehiclesResponse
import com.example.freenowapp.remote.model.toVehicleUIModel
import com.example.freenowapp.utils.ResponseStatues
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
internal class VehiclesViewModelTest {

    private lateinit var vehiclesViewModel: VehiclesViewModel

    @Mock
    private lateinit var getVehicles: GetVehicles

    @BeforeEach
    fun setUp() {
        getVehicles = Mockito.mock(GetVehicles::class.java)
        runBlocking {
            Mockito.`when`(
                getVehicles.execute(53.694865, 9.757589, 53.394655, 10.099891)
            ).thenReturn(ResponseStatues.success(fakeVehiclesResponse.vehicles))
        }
            vehiclesViewModel = VehiclesViewModel(getVehicles)

    }
    @Test
    fun `when get vehicles then vehicle list  is returned with all required info `() {

        assertEquals(
            fakeVehiclesResponse.vehicles.map { it.toVehicleUIModel() },
            vehiclesViewModel.vehicles.value
        )
    }
}