package serg.chuprin.finances.core.api.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Sergey Chuprin on 08.04.2020.
 */
fun <T> flowOfSingleValue(block: suspend () -> T): Flow<T> {
    return flow {
        emit(block())
    }
}