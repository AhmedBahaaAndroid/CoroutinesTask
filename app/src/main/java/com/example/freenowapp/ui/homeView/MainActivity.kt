package com.example.freenowapp.ui.homeView

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import com.example.freenowapp.R
import com.example.freenowapp.databinding.ActivityMainBinding
import com.example.freenowapp.ui.homeView.fragments.VehicleOnMapFragment
import com.example.freenowapp.ui.homeView.viewModel.VehiclesViewModel
import com.example.freenowapp.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: VehiclesViewModel by viewModel(VehiclesViewModel::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentLayout = layoutInflater.inflate(R.layout.activity_main, null, false)
        super.setContentView(contentLayout)
        binding = DataBindingUtil.bind(contentLayout) ?: return
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, VehicleOnMapFragment.newInstance())
            .commitNow()
        binding.lifecycleOwner = this

    }

}