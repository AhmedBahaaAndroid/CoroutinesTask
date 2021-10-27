package com.example.freenowapp.ui.homeView.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.freenowapp.ui.homeView.uiModel.Vehicle

class VehiclesSharedViewModel : ViewModel() {
    private val _selectedVehicle = MutableLiveData<Vehicle>()
    val selectedVehicle: LiveData<Vehicle>
        get() = _selectedVehicle

    fun onVehicleSelected(vehicle: Vehicle) {
        _selectedVehicle.value = vehicle
    }

}