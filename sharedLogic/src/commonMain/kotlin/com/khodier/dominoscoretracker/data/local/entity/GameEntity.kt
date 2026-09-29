package com.khodier.dominoscoretracker.data.local.entity

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "games",
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["id"],
            childColumns = ["team1Id"]
        ),
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["id"],
            childColumns = ["team2Id"]
        )
    ],
    indices = [
        Index(value = ["team1Id"]),
        Index(value = ["team2Id"])
    ]
)
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val team1Id: Long,
    val team2Id: Long,
    val prize: String,
    val prizeCost: Double,
    val targetScore: Int,
    val isDone: Boolean
)