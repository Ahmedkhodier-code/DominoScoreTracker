package com.khodier.dominoscoretracker.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import com.khodier.dominoscoretracker.domain.model.Game
import com.khodier.dominoscoretracker.domain.model.Team
import com.khodier.dominoscoretracker.theme.DominoScoreTrackerTheme
import com.khodier.dominoscoretracker.ui.components.ErrorState
import com.khodier.dominoscoretracker.ui.components.LoadingState
import com.khodier.dominoscoretracker.ui.components.TeamScoreCard
import dominoscoretracker.sharedui.generated.resources.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameScreen(
    gameId: Long,
    viewModel: GameViewModel = koinViewModel(),
    onGameEnded: (Int) -> Unit = {},
    onNewGameClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(gameId) {
        viewModel.loadGame(gameId)
    }

    GameScreenContent(
        uiState = uiState,
        onGameEnded = onGameEnded,
        onNewGameClick = onNewGameClick,
        onBackClick = onBackClick,
        onAddScore = { team, score ->
            viewModel.addScore(team, score)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreenContent(
    uiState: GameUiState,
    onGameEnded: (Int) -> Unit = {},
    onNewGameClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onAddScore: (Team, Int) -> Unit = { _, _ -> }
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val surfaceContainer = MaterialTheme.colorScheme.surfaceContainer
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    val game = uiState.game
    val winnerTeam = uiState.winnerTeam

    LaunchedEffect(winnerTeam) {
        if (winnerTeam != null) {
            onGameEnded(winnerTeam.id.toInt())
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = onSurface,
                        )
                    }
                    Text(
                        text = stringResource(Res.string.active_game_screen),
                        color = onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
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

            game != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Header: Current Round + Game Title
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.SportsEsports,
                                contentDescription = null,
                                tint = primaryColor,
                                modifier = Modifier.size(24.dp),
                            )
                            Text(
                                text = game.name,
                                color = onSurface,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }

                    // Target & Prize Banner
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = surfaceContainer),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            // Target to Win
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(surfaceVariant, RoundedCornerShape(10.dp)),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Flag,
                                        contentDescription = null,
                                        tint = primaryColor,
                                        modifier = Modifier.size(20.dp),
                                    )
                                }
                                Column {
                                    Text(
                                        text = stringResource(Res.string.target_to_win),
                                        color = onSurfaceVariant,
                                        fontSize = 12.sp,
                                    )
                                    Text(
                                        text = "${game.targetScore} ${stringResource(Res.string.pts)}",
                                        color = onSurface,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }

                            // Prize
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(surfaceVariant, RoundedCornerShape(10.dp)),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.CardGiftcard,
                                        contentDescription = null,
                                        tint = secondaryColor,
                                        modifier = Modifier.size(20.dp),
                                    )
                                }
                                Column {
                                    Text(
                                        text = stringResource(Res.string.prize),
                                        color = secondaryColor,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                    )
                                    Text(
                                        text = "${game.prize} (${game.prizeCost.toInt()} EGP)",
                                        color = onSurface,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                        }
                    }

                    // Teams Side-by-Side (Team 1 vs Team 2)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        val team1IsWinner =
                            winnerTeam != null && (winnerTeam.id == game.team1.id || winnerTeam.name == game.team1.name)
                        TeamScoreCard(
                            teamLabel = stringResource(Res.string.team_1),
                            team = game.team1,
                            targetScore = game.targetScore,
                            isLeading = game.team1.score > game.team2.score,
                            isWinner = team1IsWinner,
                            accentColor = primaryColor,
                            onPrimaryColor = MaterialTheme.colorScheme.onPrimary,
                            onAddScore = onAddScore,
                            modifier = Modifier.weight(1f),
                        )

                        val team2IsWinner =
                            winnerTeam != null && (winnerTeam.id == game.team2.id || winnerTeam.name == game.team2.name)
                        TeamScoreCard(
                            teamLabel = stringResource(Res.string.team_2),
                            team = game.team2,
                            targetScore = game.targetScore,
                            isLeading = game.team2.score > game.team1.score,
                            isWinner = team2IsWinner,
                            accentColor = secondaryColor,
                            onPrimaryColor = MaterialTheme.colorScheme.onSecondary,
                            onAddScore = onAddScore,
                            modifier = Modifier.weight(1f),
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (winnerTeam != null) {
                        WinnerDialog(
                            game = game,
                            winnerTeam = winnerTeam,
                            onNewGameClick = onNewGameClick,
                            onFinishReturnHomeClick = onBackClick,
                        )
                    }
                }
            }
        }
    }
}



@Preview(name = "Light Mode", showBackground = true)
@Composable
fun GameScreenLightPreview() {
    DominoScoreTrackerTheme(darkTheme = false) {
        GameScreenContent(
            uiState = GameUiState(
                game = Game(
                    id = 1L,
                    name = "The Big Night Match",
                    team1 = Team(id = 1L, name = "Team Knights", score = 100),
                    team2 = Team(id = 2L, name = "Team Falcons", score = 40),
                    prize = "Chips & Juice",
                    prizeCost = 50.0,
                    targetScore = 120,
                    isDone = false
                ),
                winnerTeam = Team(id = 1L, name = "Team Knights", score = 100)
            )
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun GameScreenDarkPreview() {
    DominoScoreTrackerTheme(darkTheme = true) {
        GameScreenContent(
            uiState = GameUiState(
                game = Game(
                    id = 1L,
                    name = "The Big Night Match",
                    team1 = Team(id = 1L, name = "Team Knights", score = 100),
                    team2 = Team(id = 2L, name = "Team Falcons", score = 40),
                    prize = "Chips & Juice",
                    prizeCost = 50.0,
                    targetScore = 100,
                    isDone = true
                ),
                winnerTeam = Team(id = 1L, name = "Team Knights", score = 100)
            )
        )
    }
}
