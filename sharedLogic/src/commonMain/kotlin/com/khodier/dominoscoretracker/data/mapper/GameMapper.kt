package com.khodier.dominoscoretracker.data.mapper

import com.khodier.dominoscoretracker.data.local.entity.GameEntity
import com.khodier.dominoscoretracker.domain.model.Game
import com.khodier.dominoscoretracker.domain.model.Team

fun GameEntity.toDomain(team1: Team, team2:Team): Game {
    return Game(
        id = id,
        name = name,
        team1 = team1,
        team2 = team2,
        prize = prize,
        prizeCost = prizeCost,
        targetScore = targetScore,
        isDone = isDone
    )
}

fun Game.toEntity(): GameEntity{
    return GameEntity(
        id = id,
        name = name,
        team1Id = team1.id,
        team2Id = team2.id,
        prize = prize,
        prizeCost = prizeCost,
        targetScore = targetScore,
        isDone = isDone
    )
}