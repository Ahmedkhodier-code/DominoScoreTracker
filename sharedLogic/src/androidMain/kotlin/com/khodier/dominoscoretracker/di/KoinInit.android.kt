package com.khodier.dominoscoretracker.di

import com.khodier.dominoscoretracker.data.local.appContext
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

actual fun initKoin(additionalModules: List<Module>) {
    startKoin {
        androidContext(appContext)
        modules(listOf(appModule) + additionalModules)
    }
}
