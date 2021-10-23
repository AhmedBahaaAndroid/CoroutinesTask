package com.example.freenowapp

import android.app.Application
import com.example.freenowapp.diModules.homeModule
import com.example.freenowapp.diModules.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FreeNowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FreeNowApp)
            modules(networkModule + homeModule)
        }

    }
}