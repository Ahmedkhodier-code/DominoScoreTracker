package com.khodier.dominoscoretracker.data.local.dao

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.khodier.dominoscoretracker.data.local.GameDatabase
import com.khodier.dominoscoretracker.data.local.entity.GameEntity
import com.khodier.dominoscoretracker.data.local.entity.TeamEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameDaoTest {

    private lateinit var db: GameDatabase
    private lateinit var gameDao: GameDao
    private lateinit var teamDao: TeamDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder<GameDatabase>(context)
            .setDriver(BundledSQLiteDriver())
            .build()
        gameDao = db.gameDao()
        teamDao = db.teamDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun saveGameAndGetGame_returnsSameData() = runTest {
        val team1Id = teamDao.insertTeam(TeamEntity(id = 0, name = "Team 1", score = 0))
        val team2Id = teamDao.insertTeam(TeamEntity(id = 0, name = "Team 2", score = 0))

        val gameEntity = GameEntity(
            id = 0,
            name = "Test Game",
            team1Id = team1Id,
            team2Id = team2Id,
            prize = "Chips",
            prizeCost = 20.0,
            targetScore = 100,
            isDone = false,
        )
        val savedGameId = gameDao.saveGame(gameEntity)

        val loadedGame = gameDao.getGame(savedGameId)

        assertEquals("Test Game", loadedGame.name)
        assertEquals(team1Id, loadedGame.team1Id)
        assertEquals(100, loadedGame.targetScore)
    }

    @Test
    fun updateTeamScore_persistsAfterReload() = runTest {
        val team1Id = teamDao.insertTeam(TeamEntity(id = 0, name = "Team 1", score = 0))
        val team2Id = teamDao.insertTeam(TeamEntity(id = 0, name = "Team 2", score = 0))

        val gameEntity = GameEntity(
            id = 0,
            name = "Score Test Game",
            team1Id = team1Id,
            team2Id = team2Id,
            prize = "Chips",
            prizeCost = 20.0,
            targetScore = 100,
            isDone = false,
        )
        gameDao.saveGame(gameEntity)

        teamDao.insertTeam(TeamEntity(id = team1Id, name = "Team 1", score = 40))

        val reloadedTeam1 = teamDao.getTeam(team1Id)

        assertEquals(40, reloadedTeam1.score)
    }

    @Test
    fun deleteGame_removesGameFromDatabase() = runTest {
        val team1Id = teamDao.insertTeam(TeamEntity(id = 0, name = "Team 1", score = 0))
        val team2Id = teamDao.insertTeam(TeamEntity(id = 0, name = "Team 2", score = 0))

        val gameEntity = GameEntity(
            id = 0,
            name = "Delete Test Game",
            team1Id = team1Id,
            team2Id = team2Id,
            prize = "Chips",
            prizeCost = 20.0,
            targetScore = 100,
            isDone = false,
        )
        val savedGameId = gameDao.saveGame(gameEntity)

        gameDao.deleteGameById(savedGameId)

        val allGames = gameDao.getAllGames()

        assertEquals(0, allGames.size)
    }

}