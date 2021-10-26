package com.example.freenowapp.ui.homeView.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freenowapp.domain.GetVehicles
import com.example.freenowapp.extensions.handleError
import com.example.freenowapp.ui.homeView.uiModel.VehicleUIModel
import com.example.freenowapp.ui.homeView.uiModel.toVehicleUIModel
import com.example.freenowapp.utils.Status
import com.example.freenowapp.utils.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VehiclesOnMapViewModel(private val getVehicles: GetVehicles) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    private val _vehiclesInBounds = MutableLiveData<List<VehicleUIModel>>()
    val vehiclesInBounds: LiveData<List<VehicleUIModel>>
        get() = _vehiclesInBounds

    fun getVehiclesListInBounds(p1Lat: Double, p1Lon: Double, p2Lat: Double, p2Lon: Double) {
        viewModelScope.launch(Dispatchers.Main) {
            _viewState.value = ViewState.Loading
            val response = getVehicles.execute(p1Lat, p1Lon, p2Lat, p2Lon)
            when (response.status) {
                Status.SUCCESS -> {
                    _viewState.value = ViewState.Success
                    _vehiclesInBounds.value = response.data?.map { it.toVehicleUIModel() }
                }
                Status.ERROR -> _viewState.value =
                    ViewState.Error(response.appException?.handleError())
            }
        }
    }

}