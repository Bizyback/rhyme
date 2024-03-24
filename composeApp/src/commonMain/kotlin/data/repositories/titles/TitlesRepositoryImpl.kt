package data.repositories.titles

import data.domain.AuthorDomain
import data.domain.PoemDomain
import data.domain.TitleDomain
import data.domain.toDomain
import data.helpers.DataResult
import sources.remotesource.helpers.NetworkResult
import sources.remotesource.source.titles.TitlesRemoteSource

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
class TitlesRepositoryImpl(
    private val remote: TitlesRemoteSource
) : TitlesRepository {

    override suspend fun getTitles(): DataResult<List<TitleDomain>> {
        return when (val result = remote.fetchTitles()) {
            is NetworkResult.Error -> DataResult.Error(message = result.message)
            is NetworkResult.Success ->
                DataResult.Success(data = result.data.titles.map { TitleDomain(header = it) })
        }
    }

    override suspend fun getRandomTitle(): DataResult<PoemDomain> {
        return when (val result = remote.fetchRandomTitles(count = 1)) {
            is NetworkResult.Error -> DataResult.Error(message = result.message)
            is NetworkResult.Success ->
                DataResult.Success(data = result.data.map { it.toDomain() }.first())
        }
    }
}