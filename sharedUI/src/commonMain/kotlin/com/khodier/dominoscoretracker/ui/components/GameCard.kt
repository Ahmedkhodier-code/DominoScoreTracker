package com.khodier.dominoscoretracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource

import com.khodier.dominoscoretracker.domain.model.Game
import com.khodier.dominoscoretracker.domain.model.Team
import com.khodier.dominoscoretracker.theme.DominoScoreTrackerTheme
import dominoscoretracker.sharedui.generated.resources.*

@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    game: Game,
    onClick: () -> Unit = {},
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val surfaceContainer = MaterialTheme.colorScheme.surfaceContainer
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val maxScore = maxOf(game.team1.score, game.team2.score)
    val winnerTeam = if (game.isDone) {
        if (game.team1.score >= game.team2.score) game.team1 else game.team2
    } else null

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceContainer),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            // Header Row (Status + Game Title)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Game Title + Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        imageVector = if (!game.isDone) Icons.Filled.SportsEsports else Icons.Filled.EmojiEvents,
                        contentDescription = null,
                        tint = if (!game.isDone) primaryColor else secondaryColor,
                        modifier = Modifier.size(18.dp),
                    )
                    Text(
                        text = game.name,
                        color = onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                // Status Badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (!game.isDone) primaryColor.copy(alpha = 0.15f) else surfaceVariant,
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        if (!game.isDone) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(primaryColor, CircleShape)
                            )
                            Text(
                                text = stringResource(Res.string.active_filter),
                                color = primaryColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        } else {
                            Text(
                                text = "${stringResource(Res.string.completed_filter)}${winnerTeam?.let { " (Winner: ${it.name})" } ?: ""}",
                                color = onSurfaceVariant,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                    }
                }
            }

            // Teams & Scores Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                // Team 1 Box
                val team1IsWinner = game.isDone && winnerTeam?.id == game.team1.id
                val team1IsLeading =
                    !game.isDone && game.team1.score >= game.team2.score && game.team1.score > 0
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceVariant.copy(alpha = 0.5f)),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            if (team1IsWinner) {
                                Icon(
                                    imageVector = Icons.Filled.EmojiEvents,
                                    contentDescription = null,
                                    tint = secondaryColor,
                                    modifier = Modifier.size(14.dp),
                                )
                            }
                            Text(
                                text = game.team1.name,
                                color = if (team1IsWinner) secondaryColor else onSurfaceVariant,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                            )
                        }
                        Text(
                            text = game.team1.score.toString(),
                            color = when {
                                team1IsWinner -> secondaryColor
                                team1IsLeading -> primaryColor
                                else -> onSurface
                            },
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                // Team 2 Box
                val team2IsWinner = game.isDone && winnerTeam?.id == game.team2.id
                val team2IsLeading = !game.isDone && game.team2.score > game.team1.score
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceVariant.copy(alpha = 0.5f)),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            if (team2IsWinner) {
                                Icon(
                                    imageVector = Icons.Filled.EmojiEvents,
                                    contentDescription = null,
                                    tint = secondaryColor,
                                    modifier = Modifier.size(14.dp),
                                )
                            }
                            Text(
                                text = game.team2.name,
                                color = if (team2IsWinner) secondaryColor else onSurfaceVariant,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                            )
                        }
                        Text(
                            text = game.team2.score.toString(),
                            color = when {
                                team2IsWinner -> secondaryColor
                                team2IsLeading -> primaryColor
                                else -> onSurface
                            },
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }

            // Progress Section
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = if (maxScore >= game.targetScore) stringResource(Res.string.target_reached) else stringResource(
                            Res.string.progress_to_target
                        ),
                        color = onSurfaceVariant,
                        fontSize = 11.sp,
                    )
                    Text(
                        text = "$maxScore / ${game.targetScore} ${stringResource(Res.string.pts)}",
                        color = onSurface,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                val progress =
                    (maxScore.toFloat() / game.targetScore.coerceAtLeast(1)).coerceIn(0f, 1f)
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = if (game.isDone) secondaryColor else primaryColor,
                    trackColor = surfaceVariant,
                )
            }

            // Prize Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.CardGiftcard,
                        contentDescription = null,
                        tint = secondaryColor,
                        modifier = Modifier.size(16.dp),
                    )
                    Text(
                        text = "Prize: ${game.prize}",
                        color = onSurface,
                        fontSize = 12.sp,
                    )
                }
                Text(
                    text = "${game.prizeCost.toInt()} ${stringResource(Res.string.egp)}",
                    color = secondaryColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun GameCardLightPreview() {
    DominoScoreTrackerTheme(darkTheme = false) {
        GameCard(
            game = Game(
                id = 1L,
                name = "Weekly Cafe Final",
                team1 = Team(id = 1L, name = "Team Lions", score = 65),
                team2 = Team(id = 2L, name = "Team Tigers", score = 40),
                prize = "Chips & Juice",
                prizeCost = 35.0,
                targetScore = 101,
                isDone = false
            )
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun GameCardDarkPreview() {
    DominoScoreTrackerTheme(darkTheme = true) {
        GameCard(
            game = Game(
                id = 1L,
                name = "Weekly Cafe Final",
                team1 = Team(id = 1L, name = "Team Lions", score = 65),
                team2 = Team(id = 2L, name = "Team Tigers", score = 40),
                prize = "Chips & Juice",
                prizeCost = 35.0,
                targetScore = 101,
                isDone = false
            )
        )
    }
}
