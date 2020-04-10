package serg.chuprin.finances.core.api.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import serg.chuprin.finances.core.api.extensions.flow.FlowTakeUntil

/**
 * Created by Sergey Chuprin on 08.04.2020.
 */
fun <T> flowOfSingleValue(block: suspend () -> T): Flow<T> {
    return flow {
        emit(block())
    }
}

fun <T> Flow<T>.takeUntil(otherFlow: Flow<Any>): Flow<T> {
    return FlowTakeUntil(this, otherFlow)
}