package com.khodier.dominoscoretracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.khodier.dominoscoretracker.data.local.initPreferencesDataStore
import com.khodier.dominoscoretracker.theme.DominoScoreTrackerTheme
import com.khodier.dominoscoretracker.ui.gameslist.GamesListScreenContent
import com.khodier.dominoscoretracker.ui.gameslist.GamesListUiState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initPreferencesDataStore(applicationContext)
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    DominoScoreTrackerTheme {
        GamesListScreenContent(
            uiState = GamesListUiState()
        )
    }
}
