package com.khodier.dominoscoretracker.di

import com.khodier.dominoscoretracker.data.local.GameDatabase
import com.khodier.dominoscoretracker.data.local.getDatabaseBuilder
import com.khodier.dominoscoretracker.data.repository.GameRepositoryImpl
import com.khodier.dominoscoretracker.data.repository.TeamRepositoryImpl
import com.khodier.dominoscoretracker.domain.repository.GameRepository
import com.khodier.dominoscoretracker.domain.repository.TeamRepository
import com.khodier.dominoscoretracker.domain.usecase.CheckWinnerUseCase
import com.khodier.dominoscoretracker.domain.usecase.UpdateScoreUseCase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.khodier.dominoscoretracker.data.local.createPreferencesDataStore
import com.khodier.dominoscoretracker.data.repository.RoundsRepositoryImpl
import com.khodier.dominoscoretracker.data.repository.ThemeRepositoryImpl
import com.khodier.dominoscoretracker.domain.repository.RoundsRepository
import com.khodier.dominoscoretracker.domain.repository.ThemeRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    single {
        getDatabaseBuilder()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<GameDatabase>().gameDao() }
    single { get<GameDatabase>().teamDao() }
    single { get<GameDatabase>().roundsDao() }
    single<GameRepository> { GameRepositoryImpl(get(), get(), get(), get()) }
    single<TeamRepository> { TeamRepositoryImpl(get()) }
    single<RoundsRepository> { RoundsRepositoryImpl(get(), get(), get()) }
    single { createPreferencesDataStore() }
    single<ThemeRepository> { ThemeRepositoryImpl(get()) }

    factoryOf(::UpdateScoreUseCase)
    factoryOf(::CheckWinnerUseCase)

}
 