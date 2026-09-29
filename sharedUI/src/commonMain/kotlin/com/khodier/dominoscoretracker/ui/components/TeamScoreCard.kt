package com.khodier.dominoscoretracker.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khodier.dominoscoretracker.domain.model.Team
import dominoscoretracker.sharedui.generated.resources.Res
import dominoscoretracker.sharedui.generated.resources.add_points
import dominoscoretracker.sharedui.generated.resources.leading
import dominoscoretracker.sharedui.generated.resources.pts
import dominoscoretracker.sharedui.generated.resources.round_points
import dominoscoretracker.sharedui.generated.resources.trailing
import org.jetbrains.compose.resources.stringResource

@Composable
fun TeamScoreCard(
    modifier: Modifier = Modifier,
    teamLabel: String,
    team: Team,
    targetScore: Int,
    isLeading: Boolean,
    isWinner: Boolean = false,
    accentColor: Color,
    onPrimaryColor: Color,
    onAddScore: (Team, Int) -> Unit = { _, _ -> },
) {
    val surfaceContainer = MaterialTheme.colorScheme.surfaceContainer
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    var roundPoints by remember { mutableStateOf("") }

    // Pulsing/Glowing Border stroke animation for winner
    val borderStroke = if (isWinner) rememberWinnerGlowBorder(accentColor) else null

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isWinner) accentColor.copy(alpha = 0.12f) else surfaceContainer
        ),
        border = borderStroke,
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = teamLabel,
                    color = onSurfaceVariant,
                    fontSize = 12.sp,
                )
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = when {
                        isWinner -> accentColor.copy(alpha = 0.3f)
                        isLeading -> accentColor.copy(alpha = 0.2f)
                        else -> surfaceVariant
                    },
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        if (isWinner || isLeading) {
                            Icon(
                                imageVector = Icons.Filled.EmojiEvents,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(12.dp),
                            )
                        }
                        Text(
                            text = when {
                                isWinner -> "Winner!"
                                isLeading -> stringResource(Res.string.leading)
                                else -> stringResource(Res.string.trailing)
                            },
                            color = if (isWinner || isLeading) accentColor else onSurfaceVariant,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }

            Text(
                text = team.name,
                color = onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
            )

            // Big Score Container
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isWinner) accentColor.copy(alpha = 0.25f) else surfaceVariant.copy(
                        alpha = 0.6f
                    )
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = team.score.toString(),
                        color = accentColor,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = stringResource(Res.string.pts),
                        color = onSurfaceVariant,
                        fontSize = 12.sp,
                    )
                }
            }

            // Progress Bar
            val progress = (team.score.toFloat() / targetScore.coerceAtLeast(1)).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = accentColor,
                trackColor = surfaceVariant,
            )

            // Round Points Label + Input
            Text(
                text = stringResource(Res.string.round_points),
                color = onSurfaceVariant,
                fontSize = 12.sp,
            )
            OutlinedTextField(
                value = roundPoints,
                onValueChange = { roundPoints = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = surfaceVariant,
                    focusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                    unfocusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                    focusedTextColor = onSurface,
                    unfocusedTextColor = onSurface,
                ),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                ),
                singleLine = true,
                enabled = !isWinner,
            )

            // Add Points Action Button
            Button(
                onClick = {
                    val pts = roundPoints.toIntOrNull() ?: 0
                    if (pts > 0) {
                        onAddScore(team, pts)
                        roundPoints = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !isWinner,
                colors = ButtonDefaults.buttonColors(
                    containerColor = accentColor,
                    contentColor = onPrimaryColor,
                ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                    Text(
                        text = stringResource(Res.string.add_points),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun rememberWinnerGlowBorder(accentColor: Color): BorderStroke {
    val infiniteTransition = rememberInfiniteTransition(label = "WinnerGlowTransition")
    val borderAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "WinnerGlowAlpha"
    )
    return BorderStroke(2.5.dp, accentColor.copy(alpha = borderAlpha))
}