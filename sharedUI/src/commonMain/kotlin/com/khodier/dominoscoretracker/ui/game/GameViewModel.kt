package com.khodier.dominoscoretracker.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khodier.dominoscoretracker.domain.model.Game
import com.khodier.dominoscoretracker.domain.model.Team
import com.khodier.dominoscoretracker.domain.repository.GameRepository
import com.khodier.dominoscoretracker.domain.usecase.CheckWinnerUseCase
import com.khodier.dominoscoretracker.domain.usecase.UpdateScoreUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GameUiState(
    val game: Game? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val winnerTeam: Team? = null,
)

class GameViewModel(
    private val gameRepository: GameRepository,
    private val updateScoreUseCase: UpdateScoreUseCase,
    private val checkWinnerUseCase: CheckWinnerUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun loadGame(gameId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val game = gameRepository.getGame(gameId)
                val winner = checkWinnerUseCase(game)
                _uiState.value = _uiState.value.copy(game = game, winnerTeam = winner, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun addScore(team: Team, score: Int) {
        viewModelScope.launch {
            _uiState.value.game?.let { game ->
                val updatedGame = updateScoreUseCase.invoke(game = game, team = team, score = score)
                gameRepository.saveGame(updatedGame)
                val winner = checkWinnerUseCase(updatedGame)
                _uiState.value = _uiState.value.copy(game = updatedGame, winnerTeam = winner)
                winner?.let {
                    gameRepository.updateStatus(game.id, true)
                }
            }
        }
    }
}
