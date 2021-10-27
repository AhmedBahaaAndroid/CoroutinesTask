package com.example.freenowapp.ui.homeView.viewModel

import com.example.freenowapp.*
import com.example.freenowapp.ui.homeView.uiModel.toVehicleUIModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
internal class VehiclesSharedViewModelTest {

    private lateinit var viewModel: VehiclesSharedViewModel

    @BeforeEach
    @ExperimentalCoroutinesApi
    fun setUp() {
        viewModel = VehiclesSharedViewModel()
    }

    @Test
    fun `when select vehicle by index then vehicle  is returned with all required info `() {
        viewModel.onVehicleSelected(fakeVehiclesResponse.remoteVehicles[0].toVehicleUIModel())
        assertEquals(
            fakeVehiclesResponse.remoteVehicles[0].toVehicleUIModel(),
            viewModel.selectedVehicle.value
        )
    }
}