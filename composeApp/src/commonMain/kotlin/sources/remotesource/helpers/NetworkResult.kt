package sources.remotesource.helpers

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

/**
 * project : rhyme
 * date    : Saturday 23/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */

/**
 * Helper class to enclose all api responses
 *
 * [T] the expected return type of the api request
 *
 * [Success] is returned when the if the request was successful
 *
 * [Error] is returned when the request is unsuccessful
 */
sealed interface NetworkResult<out T> {

    data class Success<T>(val data: T) : NetworkResult<T>

    data class Error(val message: String) : NetworkResult<Nothing>

}

/**
 * Helper class to catch any exception during the api request
 *
 * [error] is a default message in-case of an error and no message is returned
 *
 * [block] the function being executed to return the
 */
suspend inline fun <reified T> safeApiCall(
    error: String,
    block: () -> HttpResponse
): NetworkResult<T> {
    return try {
        val response = block.invoke()
        NetworkResult.Success(data = response.body())
    } catch (e: Exception) {
        NetworkResult.Error(message = e.message ?: error)
    }
}