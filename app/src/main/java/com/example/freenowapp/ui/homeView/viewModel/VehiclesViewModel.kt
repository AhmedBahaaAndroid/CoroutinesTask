package com.example.freenowapp.ui.homeView.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freenowapp.DEF_P1LAT
import com.example.freenowapp.DEF_P1LONG
import com.example.freenowapp.DEF_P2LAT
import com.example.freenowapp.DEF_P2LONG
import com.example.freenowapp.domain.GetVehicles
import com.example.freenowapp.remote.model.RemoteVehicleResponse
import kotlinx.coroutines.launch

class VehiclesViewModel(val getVehicles: GetVehicles) : ViewModel() {
    var vehicles = MutableLiveData<RemoteVehicleResponse>()
    init {
        viewModelScope.launch {
          vehicles.value =  getVehicles.execute(DEF_P1LAT, DEF_P1LONG, DEF_P2LAT, DEF_P2LONG)
        }

    }
}