package com.khodier.dominoscoretracker.domain.repository

import com.khodier.dominoscoretracker.domain.model.Game

interface GameRepository {
    suspend fun getAllGames(): List<Game>
    suspend fun getGame(gameId: Long): Game

    suspend fun getGamesByTeams(team1Id: Long, team2Id: Long): List<Game>
    suspend fun saveGame(game: Game) :Long
    suspend fun deleteGame(game: Game)

    suspend fun updateStatus(id: Long, isDone: Boolean)

    suspend fun getCountByStatus(isDone: Boolean): Int
}