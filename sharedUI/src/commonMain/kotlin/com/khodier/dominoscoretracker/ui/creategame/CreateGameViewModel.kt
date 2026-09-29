package com.khodier.dominoscoretracker.ui.creategame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khodier.dominoscoretracker.domain.model.Game
import com.khodier.dominoscoretracker.domain.model.Team
import com.khodier.dominoscoretracker.domain.repository.GameRepository
import com.khodier.dominoscoretracker.domain.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CreateGameUiState(
    val gameName: String = "",
    val team1Name: String = "",
    val team2Name: String = "",
    val prize: String = "",
    val prizeCost: String = "",
    val targetScore: String = "",
)

class CreateGameViewModel(
    private val gameRepository: GameRepository,
    private val teamRepository: TeamRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateGameUiState())
    val uiState: StateFlow<CreateGameUiState> = _uiState.asStateFlow()

    private val _createdGameId = MutableStateFlow<Long?>(null)
    val createdGameId: StateFlow<Long?> = _createdGameId.asStateFlow()

    fun onGameNameChange(value: String) {
        _uiState.value = _uiState.value.copy(gameName = value)
    }

    fun onTeam1NameChange(value: String) {
        _uiState.value = _uiState.value.copy(team1Name = value)
    }

    fun onTeam2NameChange(value: String) {
        _uiState.value = _uiState.value.copy(team2Name = value)
    }

    fun onPrizeChange(value: String) {
        _uiState.value = _uiState.value.copy(prize = value)
    }

    fun onPrizeCostChange(value: String) {
        _uiState.value = _uiState.value.copy(prizeCost = value)
    }

    fun onTargetScoreChange(value: String) {
        _uiState.value = _uiState.value.copy(targetScore = value)
    }

    fun onGameCreatedHandled() {
        _createdGameId.value = null
    }

    fun onCreateGameClick() {
        viewModelScope.launch {
            // Implementation for creating game
            val team1 = Team(id = 0, name = _uiState.value.team1Name, score = 0)
            val team2 = Team(id = 0, name = _uiState.value.team2Name, score = 0)
            val game = Game(
                id = 0,
                name = _uiState.value.gameName,
                team1 = team1.copy(id = teamRepository.saveTeam(team1)),
                team2 = team2.copy(id = teamRepository.saveTeam(team2)),
                prize = _uiState.value.prize,
                prizeCost = _uiState.value.prizeCost.toDoubleOrNull() ?: 0.0,
                targetScore = _uiState.value.targetScore.toIntOrNull() ?: 100,
                isDone = false
            )
            val savedGameId = gameRepository.saveGame(game)
            _createdGameId.value = savedGameId
        }
    }
}
