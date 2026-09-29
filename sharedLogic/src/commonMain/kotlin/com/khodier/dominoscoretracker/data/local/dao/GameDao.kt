package com.khodier.dominoscoretracker.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.khodier.dominoscoretracker.data.local.entity.GameEntity

@Dao
interface GameDao {
    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<GameEntity>

    @Query("SELECT * FROM games WHERE id = :gameId")
    suspend fun getGame(gameId: Long): GameEntity

    @Query("SELECT * FROM games WHERE team1Id = :team1Id AND team2Id = :team2Id")
    suspend fun getGamesByTeams(team1Id: Long, team2Id: Long): List<GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGame(game: GameEntity): Long

    @Query("DELETE FROM games WHERE id = :gameId")
    suspend fun deleteGameById(gameId: Long)

    @Query("UPDATE games SET isDone = :isDone WHERE id = :id")
    suspend fun updateStatus(id: Long, isDone: Boolean)

    @Query("SELECT COUNT(*) FROM games WHERE isDone = :isDone")
    suspend fun getCountByStatus(isDone: Boolean): Int
}