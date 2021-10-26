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
import com.example.freenowapp.ui.homeView.viewModel.VehiclesViewModel
import com.example.freenowapp.utils.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var animationLoader: LottieAnimationView
    private lateinit var errorAnimationLoader: LottieAnimationView
    val viewModel: VehiclesViewModel by viewModel(VehiclesViewModel::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentLayout = layoutInflater.inflate(R.layout.activity_main, null, false)
        super.setContentView(contentLayout)
        binding = DataBindingUtil.bind(contentLayout) ?: return
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, VehicleListFragment())
            .commitNow()
        binding.lifecycleOwner = this
        initRootView()
        initErrorView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(this, Observer {
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
        viewModel.selectedVehicle.observe(this, Observer {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, VehicleOnMapFragment.newInstance(selectedVehicle = it))
                .addToBackStack("map").commit()
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
                onBackPressed()
            }
            retryButton.setOnClickListener {
                viewModel.onRefreshData()
            }
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    companion object {
        const val LOADER_FILE_NAME = "loader.json"
        const val ERROR_LOADER_FILE_NAME = "errorimage.json"
    }

}