package com.khodier.dominoscoretracker.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "teams")
data class TeamEntity (
    @PrimaryKey(autoGenerate = true) val id:Long,
    val name:String,
    val score:Int
)