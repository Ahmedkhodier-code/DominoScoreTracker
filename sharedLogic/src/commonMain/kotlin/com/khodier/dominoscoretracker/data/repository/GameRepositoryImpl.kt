package com.khodier.dominoscoretracker.data.repository

import com.khodier.dominoscoretracker.data.local.dao.GameDao
import com.khodier.dominoscoretracker.data.local.dao.TeamDao
import com.khodier.dominoscoretracker.data.local.entity.GameEntity
import com.khodier.dominoscoretracker.data.mapper.toDomain
import com.khodier.dominoscoretracker.data.mapper.toEntity
import com.khodier.dominoscoretracker.domain.model.Game
import com.khodier.dominoscoretracker.domain.repository.GameRepository

class GameRepositoryImpl(val gameDao: GameDao, val teamDao: TeamDao) : GameRepository {
    override suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames().map {
            it.toDomainWithTeams()
        }
    }

    override suspend fun getGame(gameId: Long): Game {
        return gameDao.getGame(gameId).toDomainWithTeams()
    }

    override suspend fun getGamesByTeams(
        team1Id: Long,
        team2Id: Long
    ): List<Game> {
        return gameDao.getGamesByTeams(team1Id = team1Id, team2Id = team2Id).map {
            it.toDomainWithTeams()
        }
    }

    override suspend fun saveGame(game: Game): Long {
        teamDao.insertTeam(game.team1.toEntity())
        teamDao.insertTeam(game.team2.toEntity())
        return gameDao.saveGame(game.toEntity())
    }

    override suspend fun deleteGame(game: Game) {
        gameDao.deleteGameById(game.id)
    }

    override suspend fun updateStatus(id: Long, isDone: Boolean) {
        gameDao.updateStatus(id, isDone)
    }

    override suspend fun getCountByStatus(isDone: Boolean): Int {
        return gameDao.getCountByStatus(isDone)
    }

    private suspend fun GameEntity.toDomainWithTeams(): Game {
        val team1 = teamDao.getTeam(team1Id).toDomain()
        val team2 = teamDao.getTeam(team2Id).toDomain()
        return toDomain(team1, team2)
    }
}

