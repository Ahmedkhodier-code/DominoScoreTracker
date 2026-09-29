package com.khodier.dominoscoretracker.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

actual fun initKoin(additionalModules: List<Module>) {
    startKoin {
        modules(listOf(appModule) + additionalModules)
    }
}
