package serg.chuprin.finances.core.mvi.store.factory

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.mapper.StoreIntentToActionMapper
import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import serg.chuprin.finances.core.mvi.store.BaseStore

/**
 * Created by Sergey Chuprin on 08.12.2020.
 */
abstract class AbsStoreFactory<I, SE, A, S, E, STORE : BaseStore<I, S, E>>(
    val initialState: S,
    val reducer: StoreStateReducer<SE, S>,
    val bootstrapper: StoreBootstrapper<A>,
    val executor: StoreActionExecutor<A, S, SE, E>,
    val intentToActionMapper: StoreIntentToActionMapper<I, A>,
) : StoreFactory<I, S, E, STORE> {

    override fun create(): STORE {
        @Suppress("UNCHECKED_CAST")
        return object : BaseStateStore<I, SE, A, S, E>(
            reducer = reducer,
            executor = executor,
            initialState = initialState,
            bootstrapper = bootstrapper,
            intentToActionMapper = intentToActionMapper,
            backgroundDispatcher = Dispatchers.Default,
            reducerDispatcher = newSingleThreadContext("Reducer")
        ) {} as STORE
    }

}