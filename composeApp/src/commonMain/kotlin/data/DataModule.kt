package data

import data.repositories.titles.TitlesRepository
import data.repositories.titles.TitlesRepositoryImpl
import org.koin.dsl.module

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
val dataModule = module {
    single<TitlesRepository> { TitlesRepositoryImpl(remote = get()) }
}