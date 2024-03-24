package ui.app

import data.dataModule
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import sources.remotesource.remoteModule
import ui.uiModule

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
fun initKoin() {
    startKoin {
        modules(remoteModule, dataModule, uiModule)
    }
}