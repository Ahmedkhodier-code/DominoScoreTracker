package com.khodier.dominoscoretracker.di

class KoinHelper {
    fun initKoinForIos() {
        initKoin(listOf(uiModule))
    }
}
