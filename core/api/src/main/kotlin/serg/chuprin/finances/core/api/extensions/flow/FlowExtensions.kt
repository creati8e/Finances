package serg.chuprin.finances.core.api.extensions.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

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

fun <T> Flow<T>.distinctUntilChangedBy(keySelectors: Collection<((T) -> Any?)>): Flow<T> {
    return distinctUntilChanged { old, new ->
        keySelectors.any { keySelector ->
            keySelector(old) == keySelector(new)
        }
    }
}