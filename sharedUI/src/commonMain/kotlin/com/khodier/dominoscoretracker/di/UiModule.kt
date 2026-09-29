package com.khodier.dominoscoretracker.di

import com.khodier.dominoscoretracker.ui.creategame.CreateGameViewModel
import com.khodier.dominoscoretracker.ui.game.GameViewModel
import com.khodier.dominoscoretracker.ui.gameslist.GamesListViewModel
import com.khodier.dominoscoretracker.ui.settings.SettingsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val uiModule = module {
    factoryOf(::GamesListViewModel)
    factoryOf(::CreateGameViewModel)
    factoryOf(::GameViewModel)
    factoryOf(::SettingsViewModel)
    single { AppSettings(get()) }
}

