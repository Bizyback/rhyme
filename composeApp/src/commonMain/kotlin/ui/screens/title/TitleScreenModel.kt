package ui.screens.title

import cafe.adriel.voyager.core.model.screenModelScope
import data.domain.PoemDomain
import data.helpers.DataResult
import data.repositories.titles.TitlesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ui.helpers.FetchItemState
import ui.helpers.StatefulScreenModel

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */

data class TitleScreenState(
    val header: String? = null,
    val poem: FetchItemState<PoemDomain> = FetchItemState.Loading
)

class TitleScreenModel(
    private val repository: TitlesRepository
) : StatefulScreenModel<TitleScreenState>(TitleScreenState()) {

    var hasRunOnce : Boolean = false
    var job: Job? = null

    fun updateHeader(value: String) {
        update { copy(header = value) }
        getTitle()
        hasRunOnce = true
    }

    private fun getTitle() {
        val header = state.value.header ?: return
        if (job == null && !hasRunOnce)
            job = screenModelScope.launch {
                update { copy(poem = FetchItemState.Loading) }
                val value = when (val result = repository.getTitle(header = header)) {
                    is DataResult.Error -> FetchItemState.Error(message = result.message)
                    is DataResult.Success -> FetchItemState.Success(data = result.data)
                }
                update { copy(poem = value) }
                job = null
            }
    }

    fun onClickRetry() {
        getTitle()
    }

}