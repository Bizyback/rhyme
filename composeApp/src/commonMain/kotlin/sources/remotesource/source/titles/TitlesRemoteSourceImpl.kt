package sources.remotesource.source.titles

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import sources.remotesource.dtos.PoemDTO
import sources.remotesource.dtos.TitlesListDTO
import sources.remotesource.helpers.Endpoint
import sources.remotesource.helpers.NetworkResult
import sources.remotesource.helpers.safeApiCall

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
class TitlesRemoteSourceImpl(private val client: HttpClient) : TitlesRemoteSource {

    override suspend fun fetchTitle(header: String): NetworkResult<List<PoemDTO>> =
        safeApiCall(error = "") {
            client.get(urlString = Endpoint.Titles.Title(header = header).url)
        }

    override suspend fun fetchTitles(): NetworkResult<TitlesListDTO> =
        safeApiCall(error = "") {
            client.get(urlString = Endpoint.Titles.url)
        }

    override suspend fun fetchRandomTitles(count: Int): NetworkResult<List<PoemDTO>> =
        safeApiCall(error = "") {
            client.get(urlString = Endpoint.Random(count = count).url)
        }

}