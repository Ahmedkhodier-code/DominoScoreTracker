package com.khodier.dominoscoretracker.data.local

import androidx.room3.AutoMigration
import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import com.khodier.dominoscoretracker.data.local.dao.GameDao
import com.khodier.dominoscoretracker.data.local.dao.RoundsDao
import com.khodier.dominoscoretracker.data.local.dao.TeamDao
import com.khodier.dominoscoretracker.data.local.entity.GameEntity
import com.khodier.dominoscoretracker.data.local.entity.RoundsEntity
import com.khodier.dominoscoretracker.data.local.entity.TeamEntity

@Database(
    entities = [
        GameEntity::class,
        TeamEntity::class,
        RoundsEntity::class
    ],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
    )
@ConstructedBy(GameDatabaseConstructor::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun teamDao(): TeamDao
    abstract fun roundsDao(): RoundsDao
    companion object {
        const val DATABASE_NAME = "domino_score_tracker_db"
    }
}

@Suppress("KotlinNoActualForExpect")
expect object GameDatabaseConstructor : RoomDatabaseConstructor<GameDatabase> {
    override fun initialize(): GameDatabase
}