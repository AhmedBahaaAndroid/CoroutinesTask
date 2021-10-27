package com.example.freenowapp.ui.homeView.viewModel

import com.example.freenowapp.*
import com.example.freenowapp.domain.GetVehicles
import com.example.freenowapp.ui.homeView.uiModel.toVehicleUIModel
import com.example.freenowapp.utils.ResponseStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
internal class VehiclesOnMapViewModelTest {

    private lateinit var vehiclesOnMapViewModel: VehiclesOnMapViewModel

    @Mock
    private lateinit var getVehicles: GetVehicles

    @BeforeEach
    @ExperimentalCoroutinesApi
    fun setUp() {
        getVehicles = Mockito.mock(GetVehicles::class.java)

        vehiclesOnMapViewModel = VehiclesOnMapViewModel(getVehicles)
    }

    @Test
    fun `when get vehicles then vehicle list  is returned with all required info `() {
        runBlocking {
            Mockito.`when`(
                getVehicles.execute(
                    TEST_DEF_P1LAT,
                    TEST_DEF_P1LONG,
                    TEST_DEF_P2LAT,
                    TEST_DEF_P2LONG
                )
            ).thenReturn(ResponseStatus.success(fakeVehiclesResponse.vehicles))
        }
        vehiclesOnMapViewModel.getVehiclesListInBounds(
            TEST_DEF_P1LAT,
            TEST_DEF_P1LONG,
            TEST_DEF_P2LAT,
            TEST_DEF_P2LONG
        )
        assertEquals(
            fakeVehiclesResponse.vehicles.map { it.toVehicleUIModel() },
            vehiclesOnMapViewModel.vehiclesInBounds.value
        )
    }
}