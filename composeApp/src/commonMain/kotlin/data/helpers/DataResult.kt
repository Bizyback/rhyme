package data.helpers

import sources.remotesource.helpers.NetworkResult

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
sealed interface DataResult<out T> {

    data class Success<T>(val data: T) : DataResult<T>

    data class Error(val message: String) : DataResult<Nothing>

}