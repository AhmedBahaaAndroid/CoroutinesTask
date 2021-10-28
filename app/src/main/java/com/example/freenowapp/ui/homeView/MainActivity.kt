package com.example.freenowapp.ui.homeView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.freenowapp.R
import com.example.freenowapp.databinding.ActivityMainBinding
import com.example.freenowapp.ui.homeView.fragments.VehicleListFragment
import com.example.freenowapp.ui.homeView.fragments.VehicleOnMapFragment
import com.example.freenowapp.ui.homeView.viewModel.VehiclesSharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: VehiclesSharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

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