package com.example.freenowapp.ui.homeView.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freenowapp.bases.BaseFragment
import com.example.freenowapp.databinding.FragmentVehicalsPagerBinding
import com.example.freenowapp.ui.homeView.adapters.VehiclesAdapter
import com.example.freenowapp.ui.homeView.uiModel.Vehicle
import com.example.freenowapp.ui.homeView.viewModel.VehiclesSharedViewModel
import com.example.freenowapp.ui.homeView.viewModel.VehiclesViewModel
import com.example.freenowapp.utils.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VehicleListFragment : BaseFragment() {
    private lateinit var binding: FragmentVehicalsPagerBinding
    private lateinit var vehiclesAdapter: VehiclesAdapter
    private val viewModel: VehiclesViewModel by viewModel()
    private val sharedViewModel: VehiclesSharedViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentVehicalsPagerBinding.inflate(inflater, container, false)
        return attachToRootView(binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Error -> {
                    hideLoading()
                    showError(
                        errorMessage = getString(it.error),
                        positiveAction = { viewModel.onRefreshData() },
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
        viewModel.vehicles.observe(viewLifecycleOwner, Observer {
            setupRecyclerView(it)
        })
    }

    private fun setupRecyclerView(vehicles: List<Vehicle>) {
        vehiclesAdapter = VehiclesAdapter(vehicles,
            VehiclesAdapter.VehiclesClickListener { vehicle ->
                sharedViewModel.onVehicleSelected(vehicle)
            })

        with(binding.VehicalsRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = vehiclesAdapter
        }
    }
}