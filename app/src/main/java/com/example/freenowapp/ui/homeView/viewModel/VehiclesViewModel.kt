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
import com.example.freenowapp.remote.model.toVehicleUIModel
import com.example.freenowapp.ui.homeView.uiModel.VehicleUIModel
import com.example.freenowapp.utils.ResponseStatues
import kotlinx.coroutines.launch

class VehiclesViewModel(private val getVehicles: GetVehicles) : ViewModel() {
    private val _vehicles = MutableLiveData<ResponseStatues<List<VehicleUIModel>>>()
    val vehicles: LiveData<ResponseStatues<List<VehicleUIModel>>>
        get() = _vehicles

    init {
        getVehicles()
    }

    private fun getVehicles() {
        viewModelScope.launch {
            _vehicles.value = ResponseStatues.loading()
            try {
                val response = getVehicles.execute(DEF_P1LAT, DEF_P1LONG, DEF_P2LAT, DEF_P2LONG)
                _vehicles.value =
                    ResponseStatues.success(response.vehicles.map { it.toVehicleUIModel() })
            } catch (e: Exception) {
                _vehicles.value = ResponseStatues.error(e.toString(), null)
            }
        }
    }
}
