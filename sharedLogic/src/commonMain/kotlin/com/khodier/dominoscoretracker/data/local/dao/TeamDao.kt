package com.khodier.dominoscoretracker.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.khodier.dominoscoretracker.data.local.entity.TeamEntity

@Dao
interface TeamDao {
    @Query("SELECT * FROM teams")
    suspend fun getAllTeams(): List<TeamEntity>

    @Query("SELECT * FROM teams WHERE id = :teamId")
    suspend fun getTeam(teamId: Long): TeamEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: TeamEntity): Long

    @Query("DELETE FROM teams WHERE id = :teamId")
    suspend fun deleteTeamById(teamId: Long)

}