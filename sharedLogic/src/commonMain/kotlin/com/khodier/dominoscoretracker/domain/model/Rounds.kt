package com.khodier.dominoscoretracker.domain.model

data class Rounds(
    val id: Long,
    val gameId: Long,
    val teamId: Long,
    val points: Int
)