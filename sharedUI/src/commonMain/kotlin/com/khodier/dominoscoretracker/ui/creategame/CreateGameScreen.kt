package com.khodier.dominoscoretracker.ui.creategame

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Gamepad
import androidx.compose.material.icons.outlined.Person2
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import com.khodier.dominoscoretracker.theme.DominoScoreTrackerTheme
import dominoscoretracker.sharedui.generated.resources.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateGameScreen(
    viewModel: CreateGameViewModel = koinViewModel(),
    onGameCreated: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val createdGameId by viewModel.createdGameId.collectAsState()

    LaunchedEffect(createdGameId) {
        createdGameId?.let {
            onGameCreated(it)
            viewModel.onGameCreatedHandled()
        }
    }

    CreateGameScreenContent(
        uiState = uiState,
        onGameNameChange = viewModel::onGameNameChange,
        onTeam1NameChange = viewModel::onTeam1NameChange,
        onTeam2NameChange = viewModel::onTeam2NameChange,
        onPrizeChange = viewModel::onPrizeChange,
        onPrizeCostChange = viewModel::onPrizeCostChange,
        onTargetScoreChange = viewModel::onTargetScoreChange,
        onCreateGameClick = viewModel::onCreateGameClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGameScreenContent(
    uiState: CreateGameUiState,
    onGameNameChange: (String) -> Unit,
    onTeam1NameChange: (String) -> Unit,
    onTeam2NameChange: (String) -> Unit,
    onPrizeChange: (String) -> Unit,
    onPrizeCostChange: (String) -> Unit,
    onTargetScoreChange: (String) -> Unit,
    onCreateGameClick: () -> Unit,
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val surfaceContainer = MaterialTheme.colorScheme.surfaceContainer
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

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
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Column {
                        Text(
                            text = stringResource(Res.string.domino_counter),
                            color = primaryColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                        )
                        Text(
                            text = stringResource(Res.string.create_game),
                            color = onSurface,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // 1. CHALLENGE INFO
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = surfaceContainer),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Gamepad,
                                contentDescription = null,
                                tint = primaryColor,
                                modifier = Modifier.size(18.dp),
                            )
                            Text(
                                text = stringResource(Res.string.challenge_info),
                                color = primaryColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp,
                            )
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = stringResource(Res.string.game_name),
                                color = onSurface,
                                fontSize = 14.sp,
                            )
                            Text(
                                text = stringResource(Res.string.required),
                                color = onSurfaceVariant,
                                fontSize = 12.sp,
                            )
                        }
                        OutlinedTextField(
                            value = uiState.gameName,
                            onValueChange = onGameNameChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = stringResource(Res.string.game_name_hint),
                                    color = onSurfaceVariant.copy(alpha = 0.6f),
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = null,
                                    tint = onSurfaceVariant,
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = surfaceVariant,
                                focusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                                unfocusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                                focusedTextColor = onSurface,
                                unfocusedTextColor = onSurface,
                            ),
                            singleLine = true,
                        )
                    }
                }
            }

            // 2. COMPETITORS
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person2,
                                contentDescription = null,
                                tint = primaryColor,
                                modifier = Modifier.size(18.dp),
                            )
                            Text(
                                text = stringResource(Res.string.competitors),
                                color = primaryColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp,
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = surfaceVariant,
                        ) {
                            Text(
                                text = stringResource(Res.string.head_to_head),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                                color = onSurfaceVariant,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                    }

                    // Team 1
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(primaryColor, CircleShape)
                            )
                            Text(
                                text = stringResource(Res.string.team_1),
                                color = onSurface,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                        OutlinedTextField(
                            value = uiState.team1Name,
                            onValueChange = onTeam1NameChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = stringResource(Res.string.team_1_hint),
                                    color = onSurfaceVariant.copy(alpha = 0.6f),
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = surfaceVariant,
                                focusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                                unfocusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                                focusedTextColor = onSurface,
                                unfocusedTextColor = onSurface,
                            ),
                            singleLine = true,
                        )
                    }

                    // VS Divider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = surfaceVariant,
                        )
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = surfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp),
                        ) {
                            Text(
                                text = stringResource(Res.string.vs),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                                color = onSurfaceVariant,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = surfaceVariant,
                        )
                    }

                    // Team 2
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(secondaryColor, CircleShape)
                            )
                            Text(
                                text = stringResource(Res.string.team_2),
                                color = onSurface,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                        OutlinedTextField(
                            value = uiState.team2Name,
                            onValueChange = onTeam2NameChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = stringResource(Res.string.team_2_hint),
                                    color = onSurfaceVariant.copy(alpha = 0.6f),
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = secondaryColor,
                                unfocusedBorderColor = surfaceVariant,
                                focusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                                unfocusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                                focusedTextColor = onSurface,
                                unfocusedTextColor = onSurface,
                            ),
                            singleLine = true,
                        )
                    }
                }
            }

            // 3.PRIZE
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
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.StarOutline,
                            contentDescription = null,
                            tint = secondaryColor,
                            modifier = Modifier.size(18.dp),
                        )
                        Text(
                            text = stringResource(Res.string.prize),
                            color = secondaryColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                        )
                    }

                    // Prize Name
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = stringResource(Res.string.prize_name),
                            color = onSurface,
                            fontSize = 14.sp,
                        )
                        OutlinedTextField(
                            value = uiState.prize,
                            onValueChange = onPrizeChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = stringResource(Res.string.prize_name_hint),
                                    color = onSurfaceVariant.copy(alpha = 0.6f),
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.CardGiftcard,
                                    contentDescription = null,
                                    tint = onSurfaceVariant,
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = secondaryColor,
                                unfocusedBorderColor = surfaceVariant,
                                focusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                                unfocusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                                focusedTextColor = onSurface,
                                unfocusedTextColor = onSurface,
                            ),
                            singleLine = true,
                        )
                    }

                    // Prize Cost
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = stringResource(Res.string.prize_cost),
                            color = onSurface,
                            fontSize = 14.sp,
                        )
                        OutlinedTextField(
                            value = uiState.prizeCost,
                            onValueChange = onPrizeCostChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = stringResource(Res.string.prize_cost_hint),
                                    color = onSurfaceVariant.copy(alpha = 0.6f),
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.AttachMoney,
                                    contentDescription = null,
                                    tint = onSurfaceVariant,
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = secondaryColor,
                                unfocusedBorderColor = surfaceVariant,
                                focusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                                unfocusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                                focusedTextColor = onSurface,
                                unfocusedTextColor = onSurface,
                            ),
                            singleLine = true,
                        )
                    }
                }
            }

            // 4. TARGET CONDITION
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Flag,
                                contentDescription = null,
                                tint = primaryColor,
                                modifier = Modifier.size(18.dp),
                            )
                            Text(
                                text = stringResource(Res.string.target_condition),
                                color = primaryColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp,
                            )
                        }
                        Text(
                            text = stringResource(Res.string.points_to_win),
                            color = onSurfaceVariant,
                            fontSize = 12.sp,
                        )
                    }

                    Text(
                        text = stringResource(Res.string.target_score_to_win),
                        color = onSurface,
                        fontSize = 14.sp,
                    )

                    // Stepper Row (- value +)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = {
                                val current = uiState.targetScore.toIntOrNull() ?: 101
                                if (current > 1) {
                                    onTargetScoreChange((current - 1).toString())
                                }
                            },
                            modifier = Modifier
                                .size(56.dp)
                                .background(
                                    surfaceVariant.copy(alpha = 0.8f),
                                    RoundedCornerShape(12.dp)
                                ),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Decrease",
                                tint = onSurface,
                            )
                        }

                        OutlinedTextField(
                            value = uiState.targetScore,
                            onValueChange = onTargetScoreChange,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = surfaceVariant,
                                focusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                                unfocusedContainerColor = surfaceVariant.copy(alpha = 0.4f),
                                focusedTextColor = primaryColor,
                                unfocusedTextColor = primaryColor,
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            ),
                            singleLine = true,
                        )

                        IconButton(
                            onClick = {
                                val current = uiState.targetScore.toIntOrNull() ?: 101
                                onTargetScoreChange((current + 1).toString())
                            },
                            modifier = Modifier
                                .size(56.dp)
                                .background(
                                    surfaceVariant.copy(alpha = 0.8f),
                                    RoundedCornerShape(12.dp)
                                ),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Increase",
                                tint = onSurface,
                            )
                        }
                    }

                    // Preset buttons row (50, 100, 150, 200)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        val presets = listOf("50", "100", "150", "200")
                        presets.forEach { preset ->
                            val isSelected = uiState.targetScore == preset
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp)
                                    .clickable { onTargetScoreChange(preset) },
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) primaryColor else surfaceVariant.copy(alpha = 0.6f),
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = preset,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else onSurface,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Create Game Btn
            Button(
                onClick = { onCreateGameClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = onPrimary,
                ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.SportsEsports,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                    Text(
                        text = stringResource(Res.string.create_game),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(name = "Light Mode", showBackground = true, heightDp = 1200)
@Composable
fun CreateGameScreenLightPreview() {
    DominoScoreTrackerTheme(darkTheme = false) {
        CreateGameScreenContent(
            uiState = CreateGameUiState(
                gameName = "Weekend Match",
                team1Name = "Falcons",
                team2Name = "Knights",
                prize = "Chips & juice",
                prizeCost = "50",
                targetScore = "151",
            ),
            onGameNameChange = {},
            onTeam1NameChange = {},
            onTeam2NameChange = {},
            onPrizeChange = {},
            onPrizeCostChange = {},
            onTargetScoreChange = {},
            onCreateGameClick = {}
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun CreateGameScreenDarkPreview() {
    DominoScoreTrackerTheme(darkTheme = true) {
        CreateGameScreenContent(
            uiState = CreateGameUiState(
                gameName = "Weekend Match",
                team1Name = "Falcons",
                team2Name = "Knights",
                prize = "Chips & juice",
                prizeCost = "50",
                targetScore = "101",
            ),
            onGameNameChange = {},
            onTeam1NameChange = {},
            onTeam2NameChange = {},
            onPrizeChange = {},
            onPrizeCostChange = {},
            onTargetScoreChange = {},
            onCreateGameClick = {}

        )
    }
}
