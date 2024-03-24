package sources.remotesource.helpers

/**
 * project : rhyme
 * date    : Saturday 23/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
sealed class Endpoint(private val route: String) {

    /**
     * The complete url endpoint
     */
    val url: String
        get() = buildString {
            append("https://poetrydb.org")
            append(route)
        }

    data object Authors : Endpoint(route = "/author") {

        data class Author(val name: String) : Endpoint(route = "/author/$name")

    }

    data object Titles : Endpoint(route = "/title") {

        data class Title(val header: String) : Endpoint(route = "/title/$header")

    }

    data class Random(val count: Int) : Endpoint(route = "random/$count")

}