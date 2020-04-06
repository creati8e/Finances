package serg.chuprin.finances.core.api.presentation.model.mvi.executor

import androidx.core.util.Consumer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Created by Sergey Chuprin on 09.08.2019.
 *
 * Executor it is a component which receives an action of type [A] and
 * does some business logic and then produces result called "side-effect" of type [SE].
 */
typealias StoreActionExecutor<T, S, SE, E> = (T, S, Consumer<E>) -> Flow<SE>

inline fun <SE : Any> event(body: () -> Unit): Flow<SE> {
    body()
    return emptyFlow()
}

fun <SE> emptyObservable(): Flow<SE> = emptyFlow()