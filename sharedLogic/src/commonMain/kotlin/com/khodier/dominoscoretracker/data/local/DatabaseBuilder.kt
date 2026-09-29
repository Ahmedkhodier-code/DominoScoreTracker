package com.khodier.dominoscoretracker.data.local

import androidx.room3.RoomDatabase

expect fun getDatabaseBuilder(): RoomDatabase.Builder<GameDatabase>