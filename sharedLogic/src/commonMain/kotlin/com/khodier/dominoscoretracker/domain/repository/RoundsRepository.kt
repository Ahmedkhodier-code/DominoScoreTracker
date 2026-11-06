package com.khodier.dominoscoretracker.domain.repository

import com.khodier.dominoscoretracker.domain.model.Rounds

interface RoundsRepository {
    suspend fun getTeamRounds(teamId: Long, gameId: Long): List<Rounds>
    suspend fun saveRounds(rounds: Rounds): Long
    suspend fun deleteRoundsByGameId(gameId: Long)
    suspend fun undoLastRound(gameId: Long): Rounds?
    suspend fun addRound(round: Rounds): Long
    suspend fun getRounds(gameId: Long): List<Rounds>
}