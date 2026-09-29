package com.khodier.dominoscoretracker.data.mapper

import com.khodier.dominoscoretracker.data.local.entity.TeamEntity
import com.khodier.dominoscoretracker.domain.model.Team

fun TeamEntity.toDomain() : Team {
    return Team(
        id = id,
        name = name,
        score = score
    )
}

fun Team.toEntity() : TeamEntity {
    return TeamEntity(
        id = id,
        name = name,
        score = score
    )
}