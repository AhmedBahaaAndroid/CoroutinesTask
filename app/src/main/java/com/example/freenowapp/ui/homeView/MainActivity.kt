package com.example.freenowapp.ui.homeView

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import com.example.freenowapp.R
import com.example.freenowapp.databinding.ActivityMainBinding
import com.example.freenowapp.ui.homeView.fragments.VehicleListFragment
import com.example.freenowapp.ui.homeView.fragments.VehicleOnMapFragment
import com.example.freenowapp.ui.homeView.viewModel.VehiclesSharedViewModel
import com.example.freenowapp.ui.homeView.viewModel.VehiclesViewModel
import com.example.freenowapp.utils.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: VehiclesSharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentLayout = layoutInflater.inflate(R.layout.activity_main, null, false)
        super.setContentView(contentLayout)
        binding = DataBindingUtil.bind(contentLayout) ?: return
        supportFragmentManager.beginTransaction()
            .add(R.id.container, VehicleListFragment())
            .commitNow()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.selectedVehicle.observe(this, Observer {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, VehicleOnMapFragment.newInstance(selectedVehicle = it))
                .addToBackStack("map").commit()
        })
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

}