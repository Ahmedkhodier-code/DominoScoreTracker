package com.khodier.dominoscoretracker.di

object KoinHelper {
    fun initKoinForIos() {
        initKoin(listOf(uiModule))
    }
}
