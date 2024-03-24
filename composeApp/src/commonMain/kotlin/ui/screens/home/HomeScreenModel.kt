package ui.screens.home

import cafe.adriel.voyager.core.model.screenModelScope
import data.domain.TitleDomain
import data.helpers.DataResult
import data.repositories.titles.TitlesRepository
import kotlinx.coroutines.launch
import ui.helpers.FetchItemState
import ui.helpers.StatefulScreenModel

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
data class HomeScreenState(
    val titles: FetchItemState<List<TitleDomain>> = FetchItemState.Loading
)

class HomeScreenModel(
    val repository: TitlesRepository
) : StatefulScreenModel<HomeScreenState>(HomeScreenState()) {

    init {
        getCurrentTitles()
    }

    private fun getCurrentTitles() {
        update { copy(titles = FetchItemState.Loading) }
        screenModelScope.launch {
            val result = repository.getTitles()
            val value = when (result) {
                is DataResult.Error -> FetchItemState.Error(message = result.message)
                is DataResult.Success -> FetchItemState.Success(data = result.data)
            }
            update { copy(titles = value) }
        }
    }

    fun onClickRetryClicked(){
        getCurrentTitles()
    }

}