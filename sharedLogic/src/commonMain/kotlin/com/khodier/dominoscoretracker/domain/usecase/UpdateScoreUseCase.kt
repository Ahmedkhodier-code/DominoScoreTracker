package com.khodier.dominoscoretracker.domain.usecase

import com.khodier.dominoscoretracker.domain.model.Game
import com.khodier.dominoscoretracker.domain.model.Team

class UpdateScoreUseCase {
    operator fun invoke(game: Game, team: Team, score: Int): Game {
        val updatedGame = if (team.id == game.team1.id) {
            game.copy(team1 = game.team1.copy(score = game.team1.score + score))
        } else {
            game.copy(team2 = game.team2.copy(score = game.team2.score + score))
        }
        return updatedGame
    }
}