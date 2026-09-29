package com.khodier.dominoscoretracker.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import com.khodier.dominoscoretracker.domain.model.Game
import com.khodier.dominoscoretracker.domain.model.Team
import com.khodier.dominoscoretracker.theme.DominoScoreTrackerTheme
import dominoscoretracker.sharedui.generated.resources.*

@Composable
fun WinnerDialog(
    game: Game,
    winnerTeam: Team,
    onNewGameClick: () -> Unit,
    onFinishReturnHomeClick: () -> Unit,
    onDismissRequest: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        WinnerDialogContent(
            game = game,
            winnerTeam = winnerTeam,
            onNewGameClick = onNewGameClick,
            onFinishReturnHomeClick = onFinishReturnHomeClick,
            onCloseClick = onDismissRequest,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WinnerDialogContent(
    game: Game,
    winnerTeam: Team,
    onNewGameClick: () -> Unit = {},
    onFinishReturnHomeClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val surfaceContainer = MaterialTheme.colorScheme.surfaceContainer
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val loserTeam = if (winnerTeam.id == game.team1.id) game.team2 else game.team1

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = surfaceContainer),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Celebration Header Icon Box
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(surfaceVariant, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "🎉",
                            fontSize = 32.sp,
                        )
                    }

                    // Subtitle & Winner Title
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = stringResource(Res.string.match_end),
                            color = primaryColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                        )
                        Text(
                            text = winnerTeam.name,
                            color = onSurface,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }

                    // Score & Prize Details Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = surfaceVariant.copy(alpha = 0.5f)
                        ),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                        ) {
                            // Final Score Section
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                ) {
                                    Text(
                                        text = winnerTeam.score.toString(),
                                        color = primaryColor,
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(
                                        text = " - ",
                                        color = onSurface,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(
                                        text = loserTeam.score.toString(),
                                        color = onSurface,
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }

                                Text(
                                    text = stringResource(Res.string.final_score),
                                    color = onSurfaceVariant,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                            }

                            HorizontalDivider(color = surfaceVariant)

                            // Prize Details Section
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .background(
                                            secondaryColor.copy(alpha = 0.2f),
                                            RoundedCornerShape(12.dp)
                                        ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.CardGiftcard,
                                        contentDescription = null,
                                        tint = secondaryColor,
                                        modifier = Modifier.size(24.dp),
                                    )
                                }

                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    ) {
                                        Text(
                                            text = stringResource(Res.string.earned_prize),
                                            color = secondaryColor,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Text(
                                            text = game.prize,
                                            color = onSurface,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                    Text(
                                        text = "${game.prizeCost.toInt()} ${
                                            stringResource(
                                                Res.string.egp
                                            )
                                        }",
                                        color = onSurfaceVariant,
                                        fontSize = 12.sp,
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        // New Game Button
                        Button(
                            onClick = onNewGameClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                )
                                Text(
                                    text = stringResource(Res.string.new_game),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }

                        // Finish & Return Button
                        Button(
                            onClick = onFinishReturnHomeClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = surfaceVariant,
                                contentColor = onSurface,
                            ),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = null,
                                    tint = onSurfaceVariant,
                                    modifier = Modifier.size(20.dp),
                                )
                                Text(
                                    text = stringResource(Res.string.finish_and_return),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                )
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
fun WinnerDialogLightPreview() {
    DominoScoreTrackerTheme(darkTheme = false) {
        WinnerDialogContent(
            game = Game(
                id = 1L,
                name = "تحدي الجمعة",
                team1 = Team(id = 1L, name = "فريق الفرسان", score = 105),
                team2 = Team(id = 2L, name = "فريق الصقور", score = 80),
                prize = "شيبسي وعصير",
                prizeCost = 50.0,
                targetScore = 100,
                isDone = true
            ),
            winnerTeam = Team(id = 1L, name = "فريق الفرسان", score = 105)
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun WinnerDialogDarkPreview() {
    DominoScoreTrackerTheme(darkTheme = true) {
        WinnerDialogContent(
            game = Game(
                id = 1L,
                name = "تحدي الجمعة",
                team1 = Team(id = 1L, name = "فريق الفرسان", score = 105),
                team2 = Team(id = 2L, name = "فريق الصقور", score = 80),
                prize = "شيبسي وعصير",
                prizeCost = 50.0,
                targetScore = 100,
                isDone = true
            ),
            winnerTeam = Team(id = 1L, name = "فريق الفرسان", score = 105)
        )
    }
}
