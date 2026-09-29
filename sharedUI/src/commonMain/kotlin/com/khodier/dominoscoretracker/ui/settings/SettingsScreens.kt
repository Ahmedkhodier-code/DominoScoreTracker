package com.khodier.dominoscoretracker.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import com.khodier.dominoscoretracker.theme.DominoScoreTrackerTheme
import com.khodier.dominoscoretracker.theme.ThemeOption
import dominoscoretracker.sharedui.generated.resources.*
import org.koin.compose.viewmodel.koinViewModel

enum class LanguageOption { ARABIC, ENGLISH }

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    SettingsScreenContent(
        uiState = uiState,
        onThemeSelected = viewModel::onThemeSelected,
        onLanguageSelected = viewModel::onLanguageSelected,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    uiState: SettingsUiState = SettingsUiState(),
    onThemeSelected: (ThemeOption) -> Unit = {},
    onLanguageSelected: (LanguageOption) -> Unit = {},
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceContainer = MaterialTheme.colorScheme.surfaceContainer
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(Res.string.domino_counter),
                        color = primaryColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                    )
                    Text(
                        text = stringResource(Res.string.settings),
                        color = onSurface,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {

            // 1. APPEARANCE & THEME
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.ColorLens,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        text = stringResource(Res.string.appearance_and_theme),
                        color = primaryColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceContainer),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = stringResource(Res.string.display_mode),
                                color = onSurface,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = surfaceVariant,
                            ) {
                                Text(
                                    text = stringResource(Res.string.cafe_table_tag),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    color = onSurfaceVariant,
                                    fontSize = 11.sp,
                                )
                            }
                        }

                        // 3 Theme Option Cards
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            // Dark Theme Option
                            val isDarkSelected = uiState.themeMode == ThemeOption.DARK
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onThemeSelected(ThemeOption.DARK) },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isDarkSelected) primaryColor.copy(alpha = 0.12f) else surfaceVariant,
                                border = if (isDarkSelected) BorderStroke(
                                    1.5.dp,
                                    primaryColor
                                ) else null,
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp),
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.DarkMode,
                                        contentDescription = null,
                                        tint = if (isDarkSelected) primaryColor else onSurfaceVariant,
                                        modifier = Modifier.size(24.dp),
                                    )
                                    Text(
                                        text = stringResource(Res.string.theme_dark),
                                        color = if (isDarkSelected) primaryColor else onSurface,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(
                                        text = stringResource(Res.string.theme_dark_default),
                                        color = if (isDarkSelected) primaryColor else onSurfaceVariant,
                                        fontSize = 10.sp,
                                    )
                                }
                            }

                            // Light Theme Option
                            val isLightSelected = uiState.themeMode == ThemeOption.LIGHT
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onThemeSelected(ThemeOption.LIGHT) },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isLightSelected) primaryColor.copy(alpha = 0.12f) else surfaceVariant,
                                border = if (isLightSelected) BorderStroke(
                                    1.5.dp,
                                    primaryColor
                                ) else null,
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp),
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.LightMode,
                                        contentDescription = null,
                                        tint = if (isLightSelected) primaryColor else onSurfaceVariant,
                                        modifier = Modifier.size(24.dp),
                                    )
                                    Text(
                                        text = stringResource(Res.string.theme_light),
                                        color = if (isLightSelected) primaryColor else onSurface,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }

                            // System Theme Option
                            val isSystemSelected = uiState.themeMode == ThemeOption.SYSTEM
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onThemeSelected(ThemeOption.SYSTEM) },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSystemSelected) primaryColor.copy(alpha = 0.12f) else surfaceVariant,
                                border = if (isSystemSelected) BorderStroke(
                                    1.5.dp,
                                    primaryColor
                                ) else null,
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp),
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.AutoMode,
                                        contentDescription = null,
                                        tint = if (isSystemSelected) primaryColor else onSurfaceVariant,
                                        modifier = Modifier.size(24.dp),
                                    )
                                    Text(
                                        text = stringResource(Res.string.theme_system),
                                        color = if (isSystemSelected) primaryColor else onSurface,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 2. APP & UI LANGUAGE
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Language,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        text = stringResource(Res.string.app_language),
                        color = primaryColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceContainer),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        // Arabic Language Option
                        val isArabic = uiState.language == LanguageOption.ARABIC
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onLanguageSelected(LanguageOption.ARABIC) },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isArabic) primaryColor.copy(alpha = 0.12f) else surfaceVariant,
                            border = if (isArabic) BorderStroke(1.5.dp, primaryColor) else null,
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                ) {
                                    if (isArabic) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = null,
                                            tint = primaryColor,
                                            modifier = Modifier.size(22.dp),
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Outlined.RadioButtonUnchecked,
                                            contentDescription = null,
                                            tint = onSurfaceVariant,
                                            modifier = Modifier.size(22.dp),
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = stringResource(Res.string.arabic_lang),
                                            color = if (isArabic) primaryColor else onSurface,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Text(
                                            text = stringResource(Res.string.arabic_sub),
                                            color = onSurfaceVariant,
                                            fontSize = 11.sp,
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .background(
                                            if (isArabic) primaryColor.copy(alpha = 0.2f) else surfaceVariant,
                                            RoundedCornerShape(10.dp)
                                        ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Translate,
                                        contentDescription = null,
                                        tint = if (isArabic) primaryColor else onSurfaceVariant,
                                        modifier = Modifier.size(20.dp),
                                    )
                                }
                            }
                        }

                        // English Language Option
                        val isEnglish = uiState.language == LanguageOption.ENGLISH
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onLanguageSelected(LanguageOption.ENGLISH) },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isEnglish) primaryColor.copy(alpha = 0.12f) else surfaceVariant,
                            border = if (isEnglish) BorderStroke(1.5.dp, primaryColor) else null,
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                ) {
                                    if (isEnglish) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = null,
                                            tint = primaryColor,
                                            modifier = Modifier.size(22.dp),
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Outlined.RadioButtonUnchecked,
                                            contentDescription = null,
                                            tint = onSurfaceVariant,
                                            modifier = Modifier.size(22.dp),
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = stringResource(Res.string.english_lang),
                                            color = if (isEnglish) primaryColor else onSurface,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Text(
                                            text = stringResource(Res.string.english_sub),
                                            color = onSurfaceVariant,
                                            fontSize = 11.sp,
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .background(
                                            if (isEnglish) primaryColor.copy(alpha = 0.2f) else surfaceVariant,
                                            RoundedCornerShape(10.dp)
                                        ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Language,
                                        contentDescription = null,
                                        tint = if (isEnglish) primaryColor else onSurfaceVariant,
                                        modifier = Modifier.size(20.dp),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun SettingsScreenLightPreview() {
    DominoScoreTrackerTheme(darkTheme = false) {
        SettingsScreenContent()
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun SettingsScreenDarkPreview() {
    DominoScoreTrackerTheme(darkTheme = true) {
        SettingsScreenContent()
    }
}
