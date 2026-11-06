package com.khodier.dominoscoretracker.data.local.dao

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.khodier.dominoscoretracker.data.local.GameDatabase
import com.khodier.dominoscoretracker.data.local.entity.GameEntity
import com.khodier.dominoscoretracker.data.local.entity.RoundsEntity
import com.khodier.dominoscoretracker.data.local.entity.TeamEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoundsDaoTest {

    private data class Setup(val game: GameEntity, val team1Id: Long, val team2Id: Long)

    private lateinit var db: GameDatabase
    private lateinit var gameDao: GameDao
    private lateinit var teamDao: TeamDao
    private lateinit var roundsDao: RoundsDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder<GameDatabase>(context)
            .setDriver(BundledSQLiteDriver())
            .build()
        gameDao = db.gameDao()
        teamDao = db.teamDao()
        roundsDao = db.roundsDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    private suspend fun createGame(): Setup {
        val team1Id = teamDao.insertTeam(TeamEntity(id = 0, name = "Team 1", score = 0))
        val team2Id = teamDao.insertTeam(TeamEntity(id = 0, name = "Team 2", score = 0))
        val game = GameEntity(
            id = 0,
            name = "Rounds Test Game",
            team1Id = team1Id,
            team2Id = team2Id,
            prize = "Chips",
            prizeCost = 20.0,
            targetScore = 100,
            isDone = false,
        )
        val gameId = gameDao.saveGame(game)
        return Setup(game.copy(id = gameId), team1Id, team2Id)
    }

    private suspend fun addRound(gameId: Long, teamId: Long, points: Int) {
        roundsDao.saveRounds(RoundsEntity(id = 0, gameId = gameId, teamId = teamId, points = points))
    }

    @Test
    fun getRoundsByGameId_returnsRoundsInInsertionOrder() = runTest {
        val setup = createGame()
        addRound(setup.game.id, setup.team1Id, 10)
        addRound(setup.game.id, setup.team2Id, 5)
        addRound(setup.game.id, setup.team1Id, 20)

        val rounds = roundsDao.getRoundsByGameId(setup.game.id)

        assertEquals(listOf(10, 5, 20), rounds.map { it.points })
        assertEquals(listOf(setup.team1Id, setup.team2Id, setup.team1Id), rounds.map { it.teamId })
    }

    @Test
    fun deleteLastRound_removesOnlyTheLastOne() = runTest {
        val setup = createGame()
        addRound(setup.game.id, setup.team1Id, 10)
        addRound(setup.game.id, setup.team2Id, 5)
        addRound(setup.game.id, setup.team1Id, 20)

        val last = requireNotNull(roundsDao.getLastRound(setup.game.id))
        assertEquals(20, last.points)
        roundsDao.deleteRoundById(last.id)

        val remaining = roundsDao.getRoundsByGameId(setup.game.id)
        assertEquals(listOf(10, 5), remaining.map { it.points })
    }

    @Test
    fun saveGameAgain_keepsExistingRounds() = runTest {
        val setup = createGame()
        addRound(setup.game.id, setup.team1Id, 10)
        addRound(setup.game.id, setup.team2Id, 5)

        gameDao.saveGame(setup.game.copy(name = "Renamed"))

        assertEquals(2, roundsDao.getRoundsByGameId(setup.game.id).size)
        assertEquals("Renamed", gameDao.getGame(setup.game.id).name)
    }

    @Test
    fun insertRoundForMissingGame_fails() = runTest {
        val setup = createGame()
        var failed = false

        try {
            addRound(setup.game.id + 999, setup.team1Id, 10)
        } catch (e: Exception) {
            failed = true
        }

        assertTrue(failed)
    }

    @Test
    fun getLastRound_returnsNull_whenGameHasNoRounds() = runTest {
        val setup = createGame()

        assertNull(roundsDao.getLastRound(setup.game.id))
    }
}
