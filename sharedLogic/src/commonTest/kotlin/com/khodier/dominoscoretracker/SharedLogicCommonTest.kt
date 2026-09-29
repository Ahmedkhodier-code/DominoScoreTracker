package com.khodier.dominoscoretracker

import com.khodier.dominoscoretracker.domain.model.Game
import com.khodier.dominoscoretracker.domain.model.Team
import com.khodier.dominoscoretracker.domain.usecase.CheckWinnerUseCase
import com.khodier.dominoscoretracker.domain.usecase.UpdateScoreUseCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SharedLogicCommonTest {

    private val updateScoreUseCase = UpdateScoreUseCase()
    private val checkWinnerUseCase = CheckWinnerUseCase()

    @Test
    fun team1ScoreIncrementTest() {
        // Arrange
        val team1 = Team(1, "Team 1", 0)
        val team2 = Team(2, "Team 2", 0)
        val game = Game(1, "Game 1", team1, team2, "Prize 1", 10.0, 50)

        // Act
        var newGame = updateScoreUseCase(game = game, team1, 5)
        newGame = updateScoreUseCase(game = newGame, team1, 10)

        // Assert
        assertEquals(15, newGame.team1.score)
        assertEquals(0, newGame.team2.score)
    }

    @Test
    fun team2ScoreIncrementTest() {
        // Arrange
        val team1 = Team(1, "Team 1", 0)
        val team2 = Team(2, "Team 2", 0)
        val game = Game(1, "Game 1", team1, team2, "Prize 1", 10.0, 50)

        // Act
        var newGame = updateScoreUseCase(game = game, team2, 5)
        newGame = updateScoreUseCase(game = newGame, team2, 10)

        // Assert
        assertEquals(15, newGame.team2.score)
        assertEquals(0, newGame.team1.score)
    }

    @Test
    fun team1CheckWinnerTest(){
        // Arrange
        val team1 = Team(1, "Team 1", 40)
        val team2 = Team(2, "Team 2", 40)
        val game = Game(1, "Game 1", team1, team2, "Prize 1", 10.0, 50)

        // Act
        val newGame = updateScoreUseCase(game = game, team1, 10)
        val winner = checkWinnerUseCase(newGame)

        //Assert
        assertEquals(team1.id , winner?.id)
    }

    @Test
    fun team2CheckWinnerTest(){
        // Arrange
        val team1 = Team(1, "Team 1", 40)
        val team2 = Team(2, "Team 2", 40)
        val game = Game(1, "Game 1", team1, team2, "Prize 1", 10.0, 50)

        // Act
        val newGame = updateScoreUseCase(game = game, team2, 10)
        val winner = checkWinnerUseCase(newGame)

        //Assert
        assertEquals(team2.id , winner?.id)
    }

    @Test
    fun noTeamWinTest(){
        // Arrange
        val team1 = Team(1, "Team 1", 30)
        val team2 = Team(2, "Team 2", 20)
        val game = Game(1, "Game 1", team1, team2, "Prize 1", 10.0, 50)

        // Act
        val newGame = updateScoreUseCase(game = game, team2, 10)
        val winner = checkWinnerUseCase(newGame)

        //Assert
        assertNull(winner)
    }
}