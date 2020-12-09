package serg.chuprin.finances.core.mvi.store

import kotlinx.coroutines.flow.Flow

/**
 * Created by Sergey Chuprin on 09.12.2020.
 */
interface BaseStore<I, S, E> {

    val state: S

    val stateFlow: Flow<S>

    val eventsFlow: Flow<E>

    fun dispatch(intent: I)

}