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
internal class VehiclesViewModelTest {

    private lateinit var vehiclesViewModel: VehiclesViewModel

    @Mock
    private lateinit var getVehicles: GetVehicles

    @BeforeEach
    @ExperimentalCoroutinesApi
    fun setUp() {
        getVehicles = Mockito.mock(GetVehicles::class.java)
        runBlocking {
            Mockito.`when`(
                getVehicles.execute(
                    TEST_DEF_P1LAT,
                    TEST_DEF_P1LONG,
                    TEST_DEF_P2LAT,
                    TEST_DEF_P2LONG
                )
            ).thenReturn(ResponseStatus.success(fakeVehiclesResponse.remoteVehicles))
        }
        vehiclesViewModel = VehiclesViewModel(getVehicles)
    }

    @Test
    fun `when get vehicles then vehicle list  is returned with all required info `() {
        assertEquals(
            fakeVehiclesResponse.remoteVehicles.map { it.toVehicleUIModel() },
            vehiclesViewModel.vehicles.value
        )
    }

//    @Test
//    fun `when select vehicle by index then vehicle  is returned with all required info `() {
//        vehiclesViewModel.onVehicleSelected(0)
//        assertEquals(
//            fakeVehiclesResponse.vehicles.map { it.toVehicleUIModel() }[0],
//            vehiclesViewModel.selectedVehicle.value
//        )
//    }
}