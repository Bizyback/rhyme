package sources.remotesource

import io.ktor.client.HttpClient
import org.koin.dsl.module
import sources.remotesource.client.createHttpClient
import sources.remotesource.source.titles.TitlesRemoteSource
import sources.remotesource.source.titles.TitlesRemoteSourceImpl

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */

val remoteModule = module {
    single<HttpClient> { createHttpClient() }
    single<TitlesRemoteSource> { TitlesRemoteSourceImpl(client = get()) }
}