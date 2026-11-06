package com.khodier.dominoscoretracker.data.local.entity

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "rounds",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"]
        ),
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["id"],
            childColumns = ["teamId"]
        )
    ],
    indices = [
        Index(value = ["gameId"]),
        Index(value = ["teamId"])
    ]
)
data class RoundsEntity(
    @PrimaryKey(autoGenerate = true) val id:Long,
    val gameId: Long,
    val teamId: Long,
    val points: Int
)