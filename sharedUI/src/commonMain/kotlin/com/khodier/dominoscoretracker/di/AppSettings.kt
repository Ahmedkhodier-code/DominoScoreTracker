package com.khodier.dominoscoretracker.di

import com.khodier.dominoscoretracker.domain.repository.ThemeRepository
import com.khodier.dominoscoretracker.theme.ThemeOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AppSettings(
    private val themeRepository: ThemeRepository
) {
    val themeMode: StateFlow<ThemeOption> = themeRepository.themeMode
        .stateIn(
            scope = CoroutineScope(Dispatchers.Main + SupervisorJob()),
            started = SharingStarted.Eagerly,
            initialValue = ThemeOption.DARK
        )

    suspend fun setTheme(theme: ThemeOption) {
        themeRepository.setTheme(theme)
    }
}