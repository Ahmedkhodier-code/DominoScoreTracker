package com.khodier.dominoscoretracker.ui.gameslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khodier.dominoscoretracker.domain.model.Game
import com.khodier.dominoscoretracker.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class GameFilter { ALL, ONGOING, COMPLETED }

data class GamesListUiState(
    val games: List<Game> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedFilter: GameFilter = GameFilter.ALL,
)

class GamesListViewModel(
    private val gameRepository: GameRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GamesListUiState())
    val uiState: StateFlow<GamesListUiState> = _uiState.asStateFlow()

    fun loadGames() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val games = gameRepository.getAllGames()
                _uiState.value = _uiState.value.copy(games = games, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun onFilterSelected(filter: GameFilter) {
        _uiState.value = _uiState.value.copy(selectedFilter = filter)
    }
}
