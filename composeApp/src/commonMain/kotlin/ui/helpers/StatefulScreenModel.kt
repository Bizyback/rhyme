package ui.helpers

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
open class StatefulScreenModel<T>(initial: T) : StateScreenModel<T>(initial) {

    fun update(block: T.() -> T) {
        mutableState.update(block)
    }

    fun update(value: T){
        mutableState.update { value }
    }

}