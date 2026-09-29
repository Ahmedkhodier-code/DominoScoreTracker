package com.khodier.dominoscoretracker.domain.usecase

import com.khodier.dominoscoretracker.domain.model.Game
import com.khodier.dominoscoretracker.domain.model.Team

class CheckWinnerUseCase {
    operator fun invoke(game: Game) : Team?{
        return when{
            game.team1.score >= game.targetScore -> game.team1
            game.team2.score >= game.targetScore -> game.team2
            else -> null
        }
    }
}