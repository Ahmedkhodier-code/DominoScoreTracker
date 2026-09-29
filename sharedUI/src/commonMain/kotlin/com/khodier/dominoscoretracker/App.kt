package com.khodier.dominoscoretracker

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.khodier.dominoscoretracker.di.AppSettings
import com.khodier.dominoscoretracker.navigation.GameNavHost
import com.khodier.dominoscoretracker.theme.DominoScoreTrackerTheme
import com.khodier.dominoscoretracker.theme.ThemeOption
import org.koin.compose.koinInject

@Composable
fun App() {
    val appSettings = koinInject<AppSettings>()
    val themeMode by appSettings.themeMode.collectAsState()

    DominoScoreTrackerTheme(
        darkTheme = when (themeMode) {
            ThemeOption.DARK -> true
            ThemeOption.LIGHT -> false
            ThemeOption.SYSTEM -> isSystemInDarkTheme()
        }
    ) {
        GameNavHost()
    }
}
