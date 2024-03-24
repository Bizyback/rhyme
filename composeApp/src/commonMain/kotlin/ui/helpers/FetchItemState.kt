package ui.helpers

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
sealed interface FetchItemState<out T> {

    data object Loading: FetchItemState<Nothing>

    data class Error(val message: String) : FetchItemState<Nothing>

    data class Success<T>(val data : T): FetchItemState<T>

}