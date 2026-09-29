package com.khodier.dominoscoretracker.ui.gameslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import com.khodier.dominoscoretracker.domain.model.Game
import com.khodier.dominoscoretracker.domain.model.Team
import com.khodier.dominoscoretracker.theme.DominoScoreTrackerTheme
import com.khodier.dominoscoretracker.ui.components.ErrorState
import com.khodier.dominoscoretracker.ui.components.GameCard
import com.khodier.dominoscoretracker.ui.components.LoadingState
import dominoscoretracker.sharedui.generated.resources.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GamesListScreen(
    viewModel: GamesListViewModel = koinViewModel(),
    onGameClick: (Long) -> Unit,
    onCreateGameClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadGames()
    }

    GamesListScreenContent(
        uiState = uiState,
        onGameClick = onGameClick,
        onCreateGameClick = onCreateGameClick,
        onFilterSelected = viewModel::onFilterSelected,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesListScreenContent(
    uiState: GamesListUiState,
    onGameClick: (Long) -> Unit = {},
    onCreateGameClick: () -> Unit = {},
    onFilterSelected: (GameFilter) -> Unit = {},
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val surfaceContainer = MaterialTheme.colorScheme.surfaceContainer
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val gamesList = uiState.games
    val activeCount = gamesList.count { !it.isDone }
    val completedCount = gamesList.count { it.isDone }
    val totalCount = gamesList.size

    val filteredGames = when (uiState.selectedFilter) {
        GameFilter.ALL -> gamesList
        GameFilter.ONGOING -> gamesList.filter { !it.isDone }
        GameFilter.COMPLETED -> gamesList.filter { it.isDone }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Image(
                        painter = painterResource(Res.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Text(
                        text = stringResource(Res.string.games_list),
                        color = onSurface,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        },

        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreateGameClick,
                containerColor = primaryColor,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(24.dp),
                icon = { Icon(Icons.Filled.SportsEsports, contentDescription = null) },
                text = {
                    Text(
                        text = stringResource(Res.string.new_game),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                LoadingState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            uiState.error != null -> {
                ErrorState(
                    message = uiState.error,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // 1. Stat Cards Row (3 Cards)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        // Total Games
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = surfaceContainer),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Text(
                                    text = stringResource(Res.string.total_games),
                                    color = onSurfaceVariant,
                                    fontSize = 11.sp,
                                )
                                Text(
                                    text = totalCount.toString(),
                                    color = primaryColor,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }

                        // Active Games
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = surfaceContainer),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Text(
                                    text = stringResource(Res.string.active_games),
                                    color = onSurfaceVariant,
                                    fontSize = 11.sp,
                                )
                                Text(
                                    text = activeCount.toString(),
                                    color = secondaryColor,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }

                        // Completed Games
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = surfaceContainer),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Text(
                                    text = stringResource(Res.string.completed),
                                    color = onSurfaceVariant,
                                    fontSize = 11.sp,
                                )
                                Text(
                                    text = completedCount.toString(),
                                    color = onSurface,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }

                    // 2. Filter Pills Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        // All Filter
                        val isAllSelected = uiState.selectedFilter == GameFilter.ALL
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .clickable { onFilterSelected(GameFilter.ALL) },
                            shape = RoundedCornerShape(20.dp),
                            color = if (isAllSelected) primaryColor else surfaceContainer,
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${stringResource(Res.string.all_filter)} ($totalCount)",
                                    color = if (isAllSelected) MaterialTheme.colorScheme.onPrimary else onSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }

                        // Active Filter
                        val isOngoingSelected = uiState.selectedFilter == GameFilter.ONGOING
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .clickable { onFilterSelected(GameFilter.ONGOING) },
                            shape = RoundedCornerShape(20.dp),
                            color = if (isOngoingSelected) primaryColor else surfaceContainer,
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${stringResource(Res.string.active_filter)} ($activeCount)",
                                    color = if (isOngoingSelected) MaterialTheme.colorScheme.onPrimary else onSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }

                        // Completed Filter
                        val isCompletedSelected = uiState.selectedFilter == GameFilter.COMPLETED
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .clickable { onFilterSelected(GameFilter.COMPLETED) },
                            shape = RoundedCornerShape(20.dp),
                            color = if (isCompletedSelected) primaryColor else surfaceContainer,
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${stringResource(Res.string.completed_filter)} ($completedCount)",
                                    color = if (isCompletedSelected) MaterialTheme.colorScheme.onPrimary else onSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }

                    // 3. Games List
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        filteredGames.forEach { game ->
                            GameCard(
                                game = game,
                                onClick = { onGameClick(game.id) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun GamesListScreenLightPreview() {
    DominoScoreTrackerTheme(darkTheme = false) {
        GamesListScreenContent(uiState = GamesListUiState(games = sampleGames))
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun GamesListScreenDarkPreview() {
    DominoScoreTrackerTheme(darkTheme = true) {
        GamesListScreenContent(uiState = GamesListUiState(games = sampleGames))
    }
}

val sampleGames = listOf(
    Game(
        id = 1L,
        name = "Weekly Cafe Final",
        team1 = Team(id = 1L, name = "Team Lions", score = 65),
        team2 = Team(id = 2L, name = "Team Tigers", score = 40),
        prize = "Chips & Juice",
        prizeCost = 35.0,
        targetScore = 101,
        isDone = false
    ),
    Game(
        id = 2L,
        name = "Friday Derby",
        team1 = Team(id = 3L, name = "Ahmed & Sameh", score = 105),
        team2 = Team(id = 4L, name = "Mahmoud & Aly", score = 80),
        prize = "Liver Sandwiches",
        prizeCost = 90.0,
        targetScore = 100,
        isDone = true
    ),
    Game(
        id = 3L,
        name = "Speed Domino Derby",
        team1 = Team(id = 5L, name = "Team Stars", score = 15),
        team2 = Team(id = 6L, name = "Team Falcons", score = 30),
        prize = "Hot Drinks",
        prizeCost = 25.0,
        targetScore = 50,
        isDone = false
    )
)
