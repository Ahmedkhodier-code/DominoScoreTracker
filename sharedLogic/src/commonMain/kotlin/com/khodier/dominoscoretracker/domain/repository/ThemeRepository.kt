package com.khodier.dominoscoretracker.domain.repository


import com.khodier.dominoscoretracker.theme.ThemeOption
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    val themeMode: Flow<ThemeOption>
    suspend fun setTheme(theme: ThemeOption)
}