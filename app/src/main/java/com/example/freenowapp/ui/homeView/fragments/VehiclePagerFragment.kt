package com.example.freenowapp.ui.homeView.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.freenowapp.databinding.FragmentVehicalsPagerBinding
import com.example.freenowapp.ui.homeView.adapters.VehiclesAdapter
import com.example.freenowapp.ui.homeView.uiModel.VehicleUIModel
import com.example.freenowapp.ui.homeView.viewModel.VehiclesViewModel
import com.example.freenowapp.utils.px
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class VehiclePagerFragment : Fragment() {
    private lateinit var binding: FragmentVehicalsPagerBinding
    private lateinit var viewPagerListener: ViewPager2.OnPageChangeCallback
    private lateinit var vehicalPagerAdapter: VehiclesAdapter
    private val shareViewModel by sharedViewModel<VehiclesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentVehicalsPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        shareViewModel.vehicles.observe(viewLifecycleOwner, Observer {
            setupViewPager(it)
        })
    }

    private fun setupViewPager(vehicalUIModels: List<VehicleUIModel>) {
        viewPagerListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                shareViewModel.onVehicleSelected(position)
            }
        }
        vehicalPagerAdapter = VehiclesAdapter(vehicalUIModels)
        with(binding.viewpager) {
            adapter = vehicalPagerAdapter
            registerOnPageChangeCallback(viewPagerListener)
            val compositePageTransformer = CompositePageTransformer()
            setPageTransformer(compositePageTransformer)
            offscreenPageLimit = 1
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = 24.px
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
        }
    }
}