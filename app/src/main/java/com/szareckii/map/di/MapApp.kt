package com.szareckii.map.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MapApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
//            modules(listOf(application, mainScreen, marksScreen))
            modules(listOf(application, marksScreen))
        }
    }
}