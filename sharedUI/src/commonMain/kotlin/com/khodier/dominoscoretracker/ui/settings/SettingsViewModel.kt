package com.khodier.dominoscoretracker.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khodier.dominoscoretracker.di.AppSettings
import com.khodier.dominoscoretracker.domain.repository.ThemeRepository
import com.khodier.dominoscoretracker.theme.ThemeOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsUiState(
    val themeMode: ThemeOption = ThemeOption.DARK,
    val language: LanguageOption = LanguageOption.ARABIC,
)

class SettingsViewModel(
    private val appSettings: AppSettings,
    private val themeRepository: ThemeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = appSettings.themeMode.map {
        SettingsUiState(themeMode = it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun onThemeSelected(theme: ThemeOption) {
        viewModelScope.launch {
            appSettings.setTheme(theme)
        }
    }

    fun onLanguageSelected(language: LanguageOption) {
        _uiState.value = _uiState.value.copy(language = language)
    }
}
