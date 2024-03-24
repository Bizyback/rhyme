package data.domain

import sources.remotesource.dtos.PoemDTO

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
data class PoemDomain(
    val title: String,
    val author: String,
    val lines: List<String>,
    val count: Int
)

fun PoemDTO.toDomain() = PoemDomain(
    title = title,
    author = author,
    lines = lines,
    count = count.toIntOrNull() ?: 0
)
