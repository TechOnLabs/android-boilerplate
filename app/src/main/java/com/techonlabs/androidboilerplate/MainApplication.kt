package com.techonlabs.androidboilerplate

import android.app.Application
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        // Start Koin
        startKoin(this, listOf(myModule))
    }
}