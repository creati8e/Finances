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
typealias StoreActionExecutor<T, S, SE, E> = (
    T,
    S,
    Consumer<E>,
    /**
     * Actions flow;
     * Use if a [Flow] inside the [StoreActionExecutor] needs to be cancelled
     * or transformed due to an incoming action T.
     */
    Flow<T>
) -> Flow<SE>

inline fun <SE : Any> emptyFlowAction(body: () -> Unit): Flow<SE> {
    body()
    return emptyFlow()
}