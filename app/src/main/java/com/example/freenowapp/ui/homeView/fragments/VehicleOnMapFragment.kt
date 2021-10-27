package com.example.freenowapp.ui.homeView.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.freenowapp.R
import com.example.freenowapp.bases.BaseFragment
import com.example.freenowapp.databinding.FragmentVehicalsOnMapBinding
import com.example.freenowapp.remote.model.FleetType
import com.example.freenowapp.ui.homeView.uiModel.CoordinateUIModel
import com.example.freenowapp.ui.homeView.uiModel.VehicleUIModel
import com.example.freenowapp.ui.homeView.viewModel.VehiclesOnMapViewModel
import com.example.freenowapp.utils.ViewState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.CancelableCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel


class VehicleOnMapFragment : BaseFragment() {

    private lateinit var binding: FragmentVehicalsOnMapBinding
    private lateinit var map: GoogleMap
    private val viewModel: VehiclesOnMapViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentVehicalsOnMapBinding.inflate(inflater, container, false)
        return attachToRootView(binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Error -> {
                    hideLoading()
                    showError(
                        errorMessage = getString(it.error),
                        positiveAction = {
                            with(map.projection.visibleRegion) {
                                viewModel.onRefreshData(
                                    farLeft.latitude,
                                    farLeft.longitude,
                                    nearRight.latitude,
                                    nearRight.longitude
                                )
                            }
                        },
                        negativeAction = { requireActivity().onBackPressed() }
                    )
                }
                ViewState.Loading -> {
                    showLoading()
                }
                ViewState.Success -> {
                    hideErrorState()
                    hideLoading()
                }
            }
        })

        viewModel.vehiclesInBounds.observe(viewLifecycleOwner, Observer {
            addVehiclesInBounds(it)
        })
    }

    private fun moveToSelectedCar() {
        val vehicle = arguments?.getParcelable<VehicleUIModel>(VEHICLE_ARG)
            ?: throw IllegalArgumentException("Argument can't be null")
        addVehicleMarker(vehicle = vehicle)
        zoomToSelectedVehicle(vehicle.coordinate)
        with(map.projection.visibleRegion) {
            viewModel.getVehiclesListInBounds(
                farLeft.latitude,
                farLeft.longitude,
                nearRight.latitude,
                nearRight.longitude
            )
        }
    }

    private fun zoomToSelectedVehicle(coordinate: CoordinateUIModel?) {
        val latitude = coordinate?.latitude ?: return
        val longitude = coordinate?.longitude ?: return
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(latitude, longitude),
                MAP_ZOOM_LEVEL
            ), object : CancelableCallback {
                override fun onFinish() {
                    with(map.projection.visibleRegion) {
                        viewModel.getVehiclesListInBounds(
                            farLeft.latitude,
                            farLeft.longitude,
                            nearRight.latitude,
                            nearRight.longitude
                        )
                    }
                }

                override fun onCancel() {
                    // no thing to do here
                }
            }
        )

    }

    private fun addVehicleMarker(vehicle: VehicleUIModel) {
        val latitude = vehicle.coordinate?.latitude ?: return
        val longitude = vehicle.coordinate.longitude ?: return

        map.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .title(vehicle.fleetType?.name)
        )?.apply {
            val bitmap = vehicle.fleetType?.let { fleetType ->
                getMarkerIcon(
                    fleetType
                )
            }
            bitmap?.let {
                setIcon(BitmapDescriptorFactory.fromBitmap(it))
            }
        }
    }

    private fun addVehiclesInBounds(vehicles: List<VehicleUIModel>) {
        vehicles.forEach {
            addVehicleMarker(vehicle = it)
        }
    }

    private fun getMarkerIcon(fleetType: FleetType): Bitmap? {
        val carIconResourceId =
            if (fleetType == FleetType.POOLING) R.drawable.car_icon else R.drawable.taxi_marker
        val carIconBitmapDrawable =
            ContextCompat.getDrawable(requireContext(), carIconResourceId) as BitmapDrawable
        return Bitmap.createScaledBitmap(carIconBitmapDrawable.bitmap, 100, 100, false)
    }

    private fun setupMap() {
        val map = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        map.getMapAsync {
            this@VehicleOnMapFragment.map = it
            moveToSelectedCar()
        }
    }


    companion object {
        const val VEHICLE_ARG = "selected_vehicle"
        fun newInstance(selectedVehicle: VehicleUIModel) =
            VehicleOnMapFragment().apply {
                val bundle = Bundle().apply {
                    putParcelable(VEHICLE_ARG, selectedVehicle)
                }
                arguments = bundle
            }

        private const val MAP_ZOOM_LEVEL = 25f
    }
}