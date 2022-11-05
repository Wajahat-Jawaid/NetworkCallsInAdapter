package com.wajahat.networkcallsinadapter

import android.app.Application
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    /** Initialize the logging library */
    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}