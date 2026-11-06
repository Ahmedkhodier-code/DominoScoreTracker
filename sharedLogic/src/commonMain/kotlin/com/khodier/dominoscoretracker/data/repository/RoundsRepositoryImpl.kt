package com.khodier.dominoscoretracker.data.repository

import androidx.room3.withWriteTransaction
import com.khodier.dominoscoretracker.data.local.GameDatabase
import com.khodier.dominoscoretracker.data.local.dao.RoundsDao
import com.khodier.dominoscoretracker.data.local.dao.TeamDao
import com.khodier.dominoscoretracker.data.mapper.toDomain
import com.khodier.dominoscoretracker.data.mapper.toEntity
import com.khodier.dominoscoretracker.domain.model.Rounds
import com.khodier.dominoscoretracker.domain.repository.RoundsRepository

class RoundsRepositoryImpl(
    private val roundsDao: RoundsDao,
    private val teamDao: TeamDao,
    private val db: GameDatabase
) :
    RoundsRepository {

    override suspend fun getTeamRounds(teamId: Long, gameId: Long): List<Rounds> {
        return roundsDao.getTeamRounds(teamId, gameId).map {
            it.toDomain()
        }
    }

    override suspend fun saveRounds(rounds: Rounds): Long {
        return roundsDao.saveRounds(
            rounds.toEntity()
        )
    }

    override suspend fun deleteRoundsByGameId(gameId: Long) {
        roundsDao.deleteRoundsByGameId(gameId)
    }

    override suspend fun undoLastRound(gameId: Long): Rounds? {
        var lastRound: Rounds? = null
        db.withWriteTransaction {
            val lastRoundEntity = roundsDao.getLastRound(gameId)
            lastRoundEntity?.let {
                teamDao.updateScore(it.teamId, -it.points)
                roundsDao.deleteRoundById(it.id)
                lastRound = it.toDomain()
            }
        }
        return lastRound
    }

    override suspend fun addRound(round: Rounds): Long {
        return db.withWriteTransaction {
            teamDao.updateScore(round.teamId, round.points)
            roundsDao.saveRounds(round.toEntity())
        }
    }

    override suspend fun getRounds(gameId: Long): List<Rounds> {
        return roundsDao.getRoundsByGameId(gameId).map {
            it.toDomain()
        }
    }
}

