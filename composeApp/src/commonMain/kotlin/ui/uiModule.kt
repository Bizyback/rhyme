package ui

import org.koin.dsl.module
import ui.screens.home.HomeScreenModel

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
val uiModule = module {
    factory { HomeScreenModel(repository = get()) }
}