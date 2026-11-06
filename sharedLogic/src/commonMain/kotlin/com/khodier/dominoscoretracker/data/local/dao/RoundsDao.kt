package com.khodier.dominoscoretracker.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.khodier.dominoscoretracker.data.local.entity.RoundsEntity

@Dao
interface RoundsDao {

    @Query("SELECT * FROM rounds WHERE teamId = :teamId AND gameId = :gameId")
    suspend fun getTeamRounds(teamId: Long, gameId: Long): List<RoundsEntity>

    @Query("SELECT * FROM rounds WHERE gameId = :gameId ORDER BY id")
    suspend fun getRoundsByGameId(gameId: Long): List<RoundsEntity>

    @Query("SELECT * FROM rounds WHERE gameId = :gameId ORDER BY id DESC LIMIT 1")
    suspend fun getLastRound(gameId: Long): RoundsEntity?

    @Query("DELETE FROM rounds WHERE id = :roundId")
    suspend fun deleteRoundById(roundId: Long)

    @Insert
    suspend fun saveRounds(rounds: RoundsEntity): Long

    @Query("DELETE FROM rounds WHERE gameId = :gameId")
    suspend fun deleteRoundsByGameId(gameId: Long)

}