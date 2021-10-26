package com.example.freenowapp.ui.homeView.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freenowapp.databinding.FragmentVehicalsPagerBinding
import com.example.freenowapp.ui.homeView.adapters.VehiclesAdapter
import com.example.freenowapp.ui.homeView.uiModel.VehicleUIModel
import com.example.freenowapp.ui.homeView.viewModel.VehiclesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class VehicleListFragment : Fragment() {
    private lateinit var binding: FragmentVehicalsPagerBinding
    private lateinit var vehiclesAdapter: VehiclesAdapter
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

    private fun setupViewPager(vehicleUIModels: List<VehicleUIModel>) {
        vehiclesAdapter = VehiclesAdapter(vehicleUIModels,
            VehiclesAdapter.VehiclesClickListener { vehicleId ->
                shareViewModel.onVehicleSelected(vehicleId)
            })

        with(binding.VehicalsRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = vehiclesAdapter
        }
    }

}