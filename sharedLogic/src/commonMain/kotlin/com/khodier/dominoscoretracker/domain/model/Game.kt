package com.khodier.dominoscoretracker.domain.model

data class Game(
    val id: Long,
    val name: String,
    val team1: Team,
    val team2: Team,
    val prize: String,
    val prizeCost: Double,
    val targetScore: Int,
    val isDone:Boolean
)