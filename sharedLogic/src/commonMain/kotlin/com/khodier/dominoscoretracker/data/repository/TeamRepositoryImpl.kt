package com.khodier.dominoscoretracker.data.repository

import com.khodier.dominoscoretracker.data.local.dao.TeamDao
import com.khodier.dominoscoretracker.data.mapper.toDomain
import com.khodier.dominoscoretracker.data.mapper.toEntity
import com.khodier.dominoscoretracker.domain.model.Team
import com.khodier.dominoscoretracker.domain.repository.TeamRepository

class TeamRepositoryImpl(val teamDao: TeamDao) : TeamRepository {
    override suspend fun getAllTeams(): List<Team> =
        teamDao.getAllTeams().map { teamEntity ->
            teamEntity.toDomain()

        }

    override suspend fun getTeam(teamId: Long): Team =
        teamDao.getTeam(teamId).toDomain()


    override suspend fun saveTeam(team: Team): Long =
        teamDao.insertTeam(team.toEntity())


    override suspend fun deleteTeam(team: Team) {
        teamDao.deleteTeamById(team.id)
    }
}