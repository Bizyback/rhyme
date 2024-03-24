package data.repositories.titles

import data.domain.PoemDomain
import data.domain.TitleDomain
import data.helpers.DataResult

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
interface TitlesRepository {

    suspend fun getTitles(): DataResult<List<TitleDomain>>

    suspend fun getRandomTitle(): DataResult<PoemDomain>

}