package com.khodier.dominoscoretracker.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.jetbrains.compose.resources.StringResource
import dominoscoretracker.sharedui.generated.resources.*
import com.khodier.dominoscoretracker.ui.creategame.CreateGameScreen
import com.khodier.dominoscoretracker.ui.game.GameScreen
import com.khodier.dominoscoretracker.ui.gameslist.GamesListScreen
import com.khodier.dominoscoretracker.ui.settings.SettingsScreen


sealed class Screen(val route: String) {
    object GamesList : Screen("games_list")
    object Settings : Screen("settings_screen")
    object CreateGame : Screen("create_game")
    object GameDetail : Screen("game/{game_id}") {
        fun go(id: Long) = "game/$id"
    }
}

data class BottomNavItem(
    val screen: Screen,
    val label: StringResource,
    val icon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.GamesList, Res.string.games, Icons.AutoMirrored.Filled.List),
    BottomNavItem(Screen.Settings, Res.string.settings, Icons.Filled.Settings),
)

val mainRoutes = bottomNavItems.map { it.screen.route }.toSet()

@Composable
fun GameNavHost(
    startDestination: String = Screen.GamesList.route
) {
    val navController = rememberNavController()
    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backstackEntry?.destination?.route

    val showNavigation = currentRoute != null && currentRoute in mainRoutes

    Scaffold(
        bottomBar = {
            if (showNavigation) {
                BottomNav(currentRoute = currentRoute) { screen ->
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(Screen.GamesList.route) {
                                this.saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(padding),
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) {
            composable(
                Screen.GamesList.route,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
                popEnterTransition = { fadeIn() },
                popExitTransition = { fadeOut() }) {
                GamesListScreen(
                    onGameClick = { gameId ->
                        navController.navigate(Screen.GameDetail.go(gameId))
                    },
                    onCreateGameClick = {
                        navController.navigate(Screen.CreateGame.route)
                    }
                )
            }

            composable(
                Screen.CreateGame.route,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
                popEnterTransition = { fadeIn() },
                popExitTransition = { fadeOut() }
            ) {
                CreateGameScreen(
                    onGameCreated = { gameId ->
                        navController.navigate(Screen.GameDetail.go(gameId)) {
                            popUpTo(Screen.CreateGame.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(
                route = Screen.GameDetail.route,
                arguments = listOf(navArgument("game_id") { type = NavType.LongType }),
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
                popEnterTransition = { fadeIn() },
                popExitTransition = { fadeOut() }
            ) { backStackEntry ->
                val gameId = backStackEntry.savedStateHandle.get<Long>("game_id") ?: 0L
                GameScreen(
                    gameId = gameId,
                    onGameEnded = {},
                    onNewGameClick = {
                        navController.navigate(Screen.CreateGame.route) {
                            popUpTo(Screen.GamesList.route) {
                                this.saveState = true
                            }
                        }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = Screen.Settings.route,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
                popEnterTransition = { fadeIn() },
                popExitTransition = { fadeOut() }
            ) {
                SettingsScreen()
            }
        }
    }
}
