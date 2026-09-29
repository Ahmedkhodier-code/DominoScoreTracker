package com.khodier.dominoscoretracker.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.khodier.dominoscoretracker.domain.repository.ThemeRepository
import com.khodier.dominoscoretracker.theme.ThemeOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : ThemeRepository {

    private val themeKey = stringPreferencesKey("theme_option")

    override val themeMode: Flow<ThemeOption> = dataStore.data.map { preferences ->
        val savedTheme = preferences[themeKey] ?: ThemeOption.DARK.name
        try {
            ThemeOption.valueOf(savedTheme)
        } catch (e: Exception) {
            ThemeOption.DARK
        }
    }

    override suspend fun setTheme(theme: ThemeOption) {
        dataStore.edit { preferences ->
            preferences[themeKey] = theme.name
        }
    }
}