package com.example.freenowapp.ui.homeView.fragments

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import com.example.freenowapp.R
import com.example.freenowapp.databinding.FragmentVehicalsOnMapBinding
import com.example.freenowapp.remote.model.FleetType
import com.example.freenowapp.ui.homeView.uiModel.VehicleUIModel
import com.example.freenowapp.ui.homeView.viewModel.VehiclesViewModel
import com.example.freenowapp.utils.ViewState
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
    private lateinit var animationLoader: LottieAnimationView
    private lateinit var errorAnimationLoader: LottieAnimationView

    private lateinit var mMap: GoogleMap
    private val viewModel: VehiclesViewModel by sharedViewModel(VehiclesViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentVehicalsOnMapBinding.inflate(inflater, container, false)
        initRootView()
        initErrorView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.vehicles.observe(viewLifecycleOwner, Observer {
            showVehiclesOnMap(it)
            showPagerFragment()
        })
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Error -> {
                    hideLoading()
                    it.error?.let { errorMessage -> showErrorView(errorMessage) }
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

        viewModel.selectedVehicle.observe(viewLifecycleOwner, Observer {
            moveToSelectedCar(it)
        })
    }

    private fun hideErrorState() {
        binding.errorView.visibility = View.GONE
        errorAnimationLoader.visibility = View.GONE
        errorAnimationLoader.cancelAnimation()
    }

    private fun showErrorState() {
        binding.errorView.visibility = View.VISIBLE
        errorAnimationLoader.visibility = View.VISIBLE
        errorAnimationLoader.playAnimation()
    }

    private fun moveToSelectedCar(vehicle: VehicleUIModel) {
        val latitude = vehicle.coordinate?.latitude ?: return
        val longitude = vehicle.coordinate.longitude ?: return

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), MAP_ZOOM_LEVEL)
        )
    }

    private fun showPagerFragment() {
        val fm: FragmentManager = childFragmentManager
        val ft: FragmentTransaction = fm.beginTransaction()
        fm.beginTransaction()
        val fragTwo: Fragment = VehiclePagerFragment()
        ft.add(R.id.vehicals_containr_frame, fragTwo)
        ft.commit()
    }

    private fun showVehiclesOnMap(vehicles: List<VehicleUIModel>) {
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
        val initialLat = vehicles[0].coordinate?.latitude ?: return
        val initialLong = vehicles[0].coordinate?.longitude ?: return
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(initialLat, initialLong),
                MAP_ZOOM_LEVEL
            )
        )
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
    }

    private fun initRootView() {
        animationLoader = binding.animationLoader
        animationLoader.repeatCount = ValueAnimator.INFINITE
        animationLoader.setAnimation(LOADER_FILE_NAME)
    }

    private fun showLoading() {
        binding.errorView.visibility = View.GONE
        binding.animationView.visibility = View.VISIBLE
        animationLoader.visibility = View.VISIBLE
        animationLoader.playAnimation()
    }

    private fun hideLoading() {
        binding.animationView.visibility = View.GONE
        animationLoader.visibility = View.GONE
        animationLoader.cancelAnimation()
    }

    private fun showErrorView(@StringRes errorMessage: Int) {
        binding.errorViewState.blockingStateDescriptionLabel.text = getString(errorMessage)
        initErrorView()
        showErrorState()
    }

    private fun initErrorView() {
        errorAnimationLoader = binding.errorViewState.errorAnimatedImage
        errorAnimationLoader.repeatCount = ValueAnimator.INFINITE
        errorAnimationLoader.setAnimation(ERROR_LOADER_FILE_NAME)
        with(binding.errorViewState) {
            backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
            retryButton.setOnClickListener {
                viewModel.onRefreshData()
            }

        }
    }


    companion object {
        fun newInstance() = VehicleOnMapFragment()
        private const val MAP_ZOOM_LEVEL = 12f
        const val LOADER_FILE_NAME = "loader.json"
        const val ERROR_LOADER_FILE_NAME = "errorimage.json"
    }

}