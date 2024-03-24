package ui

import org.koin.dsl.module
import ui.screens.titles.TitlesScreenModel
import ui.screens.title.TitleScreenModel

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
val uiModule = module {
    factory { TitlesScreenModel(repository = get()) }
    factory { TitleScreenModel(repository = get()) }
}