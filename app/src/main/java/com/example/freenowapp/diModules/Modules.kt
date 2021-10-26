package com.example.freenowapp.diModules

import com.example.freenowapp.domain.GetVehicles
import com.example.freenowapp.network.RetrofitClient
import com.example.freenowapp.remote.VehiclesApi
import com.example.freenowapp.repo.VehiclesRepository
import com.example.freenowapp.ui.homeView.viewModel.VehiclesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single {
        RetrofitClient.Builder("https://fake-poi-api.mytaxi.com")
            .build {
                useDefaultLoggerInterceptor()
            }
    }
}

val homeModule = module {
    single { get<RetrofitClient>().create(VehiclesApi::class.java) }
    factory { VehiclesRepository(get()) }
    factory { GetVehicles(get()) }
    viewModel { VehiclesViewModel(get()) }
}