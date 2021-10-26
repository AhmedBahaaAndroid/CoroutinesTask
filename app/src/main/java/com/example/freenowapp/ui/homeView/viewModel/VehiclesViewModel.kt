package com.example.freenowapp.ui.homeView.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freenowapp.DEF_P1LAT
import com.example.freenowapp.DEF_P1LONG
import com.example.freenowapp.DEF_P2LAT
import com.example.freenowapp.DEF_P2LONG
import com.example.freenowapp.domain.GetVehicles
import com.example.freenowapp.ui.homeView.uiModel.VehicleUIModel
import com.example.freenowapp.ui.homeView.uiModel.toVehicleUIModel
import com.example.freenowapp.utils.Status
import com.example.freenowapp.utils.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VehiclesViewModel(private val getVehicles: GetVehicles) : ViewModel() {
    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState
    private val _vehicles = MutableLiveData<List<VehicleUIModel>>()
    val vehicles: LiveData<List<VehicleUIModel>>
        get() = _vehicles

    private val _selectedVehicle = MutableLiveData<VehicleUIModel>()
    val selectedVehicle: LiveData<VehicleUIModel>
        get() = _selectedVehicle

    init {
        getVehicles()
    }

    private fun getVehicles() {
        viewModelScope.launch(Dispatchers.Main) {
            _viewState.value = ViewState.Loading
            val response = getVehicles.execute(DEF_P1LAT, DEF_P1LONG, DEF_P2LAT, DEF_P2LONG)

            when (response.status) {
                Status.SUCCESS -> {
                    _viewState.value = ViewState.Success
                    _vehicles.value = response.data?.map { it.toVehicleUIModel() }
                }
                Status.ERROR -> _viewState.value = ViewState.Error(response.appException)
            }
        }
    }

    fun onVehicleSelected(position: Int) {
        _selectedVehicle.value = _vehicles.value?.get(position)
    }

    fun refresh() {
        getVehicles()
    }
}
