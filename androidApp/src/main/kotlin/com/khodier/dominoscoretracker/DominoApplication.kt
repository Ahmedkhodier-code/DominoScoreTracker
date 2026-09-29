package com.khodier.dominoscoretracker

import android.app.Application
import com.khodier.dominoscoretracker.data.local.appContext
import com.khodier.dominoscoretracker.di.initKoin
import com.khodier.dominoscoretracker.di.uiModule

class DominoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        initKoin(listOf(uiModule))
    }
}
