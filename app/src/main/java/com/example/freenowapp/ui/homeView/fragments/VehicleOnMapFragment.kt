package com.example.freenowapp.ui.homeView.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.freenowapp.R
import com.example.freenowapp.databinding.FragmentVehicalsOnMapBinding
import com.example.freenowapp.remote.model.FleetType
import com.example.freenowapp.ui.homeView.uiModel.VehicleUIModel
import com.example.freenowapp.ui.homeView.viewModel.VehiclesViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class VehicleOnMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentVehicalsOnMapBinding

    private lateinit var mMap: GoogleMap
    private val viewModel: VehiclesViewModel by sharedViewModel(VehiclesViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentVehicalsOnMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.vehiclesInBounds.observe(viewLifecycleOwner, Observer {
            addVehiclesInBounds(it)
        })
    }

    private fun moveToSelectedCar() {
        val vehicle =
            arguments?.getParcelable<VehicleUIModel>(VEHICLE_ARG)
                ?: throw IllegalArgumentException("Argument can't be null")
        val latitude = vehicle.coordinate?.latitude ?: return
        val longitude = vehicle.coordinate.longitude ?: return
        mMap.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .title(vehicle.fleetType?.name)
        ).apply {
            this!!.setIcon(BitmapDescriptorFactory.fromBitmap(vehicle.fleetType?.let { fleetType ->
                getMarkerIcon(
                    fleetType
                )
            }!!))
        }
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), MAP_ZOOM_LEVEL)
        )
        mMap.setOnCameraIdleListener {
            viewModel.getVehiclesListInBounds(
                mMap.projection.visibleRegion.farLeft.latitude,
                mMap.projection.visibleRegion.farLeft.longitude,
                mMap.projection.visibleRegion.nearRight.latitude,
                mMap.projection.visibleRegion.nearRight.longitude
            )
        }
    }

    private fun addVehiclesInBounds(vehicles: List<VehicleUIModel>) {
        vehicles.forEach {
            val lat = it.coordinate?.latitude ?: return
            val long = it.coordinate.longitude ?: return
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(lat, long))
                    .title(it.fleetType?.name)
            ).apply {
                this!!.setIcon(BitmapDescriptorFactory.fromBitmap(it.fleetType?.let { fleetType ->
                    getMarkerIcon(
                        fleetType
                    )
                }!!))
            }
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
        map.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        moveToSelectedCar()
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

        private const val MAP_ZOOM_LEVEL = 100f
        const val LOADER_FILE_NAME = "loader.json"
        const val ERROR_LOADER_FILE_NAME = "errorimage.json"
    }

}