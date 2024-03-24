package sources.remotesource.source.titles

import sources.remotesource.dtos.PoemDTO
import sources.remotesource.dtos.TitlesListDTO
import sources.remotesource.helpers.NetworkResult

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
interface TitlesRemoteSource {

    suspend fun fetchTitles(): NetworkResult<TitlesListDTO>

    suspend fun fetchTitle(header: String): NetworkResult<List<PoemDTO>>

    suspend fun fetchRandomTitles(count: Int): NetworkResult<List<PoemDTO>>

}