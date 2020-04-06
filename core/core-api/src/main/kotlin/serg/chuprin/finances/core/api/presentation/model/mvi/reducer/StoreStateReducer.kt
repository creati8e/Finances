package serg.chuprin.finances.core.api.presentation.model.mvi.reducer

/**
 * Created by Sergey Chuprin on 14.07.2019.
 *
 * Reducer is a component which combines current action of type [T] with current state of type [S]
 * and produces new state.
 *
 * Implementors have to not produce any side effects in this function.
 * Function must be pure by contract.
 */
typealias StoreStateReducer<T, S> = (T, S) -> S