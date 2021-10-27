package com.example.freenowapp.ui.homeView.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.freenowapp.ui.homeView.uiModel.VehicleUIModel

class VehiclesSharedViewModel : ViewModel() {
    private val _selectedVehicle = MutableLiveData<VehicleUIModel>()
    val selectedVehicle: LiveData<VehicleUIModel>
        get() = _selectedVehicle

    fun onVehicleSelected(vehicle: VehicleUIModel) {
        _selectedVehicle.value = vehicle
    }

}