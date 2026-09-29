package com.khodier.dominoscoretracker.domain.repository

import com.khodier.dominoscoretracker.domain.model.Team

interface TeamRepository {
    suspend fun getAllTeams(): List<Team>
    suspend fun getTeam(teamId: Long): Team
    suspend fun saveTeam(team: Team) :Long
    suspend fun deleteTeam(team: Team)
}