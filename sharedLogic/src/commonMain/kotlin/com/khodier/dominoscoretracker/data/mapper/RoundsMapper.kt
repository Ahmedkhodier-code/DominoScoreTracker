package com.khodier.dominoscoretracker.data.mapper

import com.khodier.dominoscoretracker.data.local.entity.RoundsEntity
import com.khodier.dominoscoretracker.domain.model.Rounds


fun RoundsEntity.toDomain(): Rounds {
    return Rounds(
        id = id,
        gameId = gameId,
        teamId = teamId,
        points = points
    )
}

fun Rounds.toEntity(): RoundsEntity {
    return RoundsEntity(
        id = id,
        gameId = gameId,
        teamId = teamId,
        points = points
    )
}