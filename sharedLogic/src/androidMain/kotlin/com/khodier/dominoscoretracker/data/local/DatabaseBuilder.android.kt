package com.khodier.dominoscoretracker.data.local

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase

lateinit var appContext: Context

actual fun getDatabaseBuilder(): RoomDatabase.Builder<GameDatabase> {
    val dbFile = appContext.getDatabasePath(GameDatabase.DATABASE_NAME)
    return Room.databaseBuilder<GameDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}