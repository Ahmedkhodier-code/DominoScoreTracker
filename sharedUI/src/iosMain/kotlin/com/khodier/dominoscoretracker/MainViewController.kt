package com.khodier.dominoscoretracker

import androidx.compose.ui.window.ComposeUIViewController
import com.khodier.dominoscoretracker.di.appModule
import com.khodier.dominoscoretracker.di.uiModule
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

private var isKoinInitialized = false

fun MainViewController(): UIViewController {
    if (!isKoinInitialized) {
        startKoin {
            modules(appModule, uiModule)
        }
        isKoinInitialized = true
    }
    return ComposeUIViewController {
        App()
    }
}
