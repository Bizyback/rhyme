package ui.screens.titles

import androidx.compose.ui.graphics.toArgb
import cafe.adriel.voyager.core.model.screenModelScope
import data.domain.TitleDomain
import data.helpers.DataResult
import data.repositories.titles.TitlesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.helpers.FetchItemState
import ui.helpers.StatefulScreenModel
import ui.theme.getRandomPastelColor

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
data class TitlesScreenState(
    val titles: FetchItemState<List<TitleDomain>> = FetchItemState.Loading,
    val title: Pair<String, Int>? = null
)

class TitlesScreenModel(
    val repository: TitlesRepository
) : StatefulScreenModel<TitlesScreenState>(TitlesScreenState()) {

    init {
        getCurrentTitles()
    }

    private fun getCurrentTitles() {
        update { copy(titles = FetchItemState.Loading) }
        screenModelScope.launch {
            val value = when (val result = repository.getTitles()) {
                is DataResult.Error -> FetchItemState.Error(message = result.message)
                is DataResult.Success -> FetchItemState.Success(data = result.data)
            }
            update { copy(titles = value) }
        }
    }

    fun onClickRetryClicked(){
        getCurrentTitles()
    }

    fun onClickGetRandomTitle() {
        val titles = state.value.titles
        if (titles is FetchItemState.Success){
            val item = titles.data.random()
            updateTitle(header = item.header, pastel = getRandomPastelColor().toArgb())
        }
    }

    private fun updateTitle(header: String, pastel: Int){
       screenModelScope.launch {
           update { copy(title = Pair(header, pastel)) }
           delay(250)
           update { copy(title = null) }
       }
    }

}