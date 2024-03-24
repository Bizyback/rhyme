package sources.remotesource.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
@Serializable
data class PoemDTO(
    val title: String,
    val author: String,
    val lines: List<String>,
    @SerialName("linecount")
    val count: String
)