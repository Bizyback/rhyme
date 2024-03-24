package sources.remotesource.dtos

import kotlinx.serialization.Serializable

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
@Serializable
data class AuthorsListDTO(val authors: List<String>)

@Serializable
data class TitlesListDTO(val titles: List<String>)