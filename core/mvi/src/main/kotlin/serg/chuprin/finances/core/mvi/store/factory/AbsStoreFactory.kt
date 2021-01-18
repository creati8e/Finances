package serg.chuprin.finances.core.mvi.store.factory

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.mapper.StoreIntentToActionMapper
import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import serg.chuprin.finances.core.mvi.store.BaseStore

/**
 * Created by Sergey Chuprin on 08.12.2020.
 *
 * Abstract factory for store store creation.
 * Preferring creation store via factory prevents code duplication for store creation
 * for tests and allows more convenient testing.
 */
abstract class AbsStoreFactory<I, SE, A, S, E, STORE : BaseStore<I, S, E>>(
    val initialState: S,
    val reducer: StoreStateReducer<SE, S>,
    val bootstrapper: StoreBootstrapper<A>,
    val actionExecutor: StoreActionExecutor<A, S, SE, E>,
    val intentToActionMapper: StoreIntentToActionMapper<I, A>,
) : StoreFactory<I, S, E, STORE> {

    protected val backgroundDispatcher: CoroutineDispatcher
        get() = Dispatchers.Default

    protected val reducerDispatcher: ExecutorCoroutineDispatcher
        get() = newSingleThreadContext("Reducer coroutine context")

    protected val bootstrapperDispatcher: ExecutorCoroutineDispatcher
        get() = newSingleThreadContext("Bootstrapper coroutine context")

    override fun create(): STORE {
        @Suppress("UNCHECKED_CAST")
        return createBaseStore() as STORE
    }

    /**
     * Not good architecture, i know.
     */
    protected fun createBaseStore(): BaseStateStore<I, SE, A, S, E> {
        return object : BaseStateStore<I, SE, A, S, E>(
            reducer = reducer,
            actionExecutor = actionExecutor,
            initialState = initialState,
            bootstrapper = bootstrapper,
            reducerDispatcher = reducerDispatcher,
            intentToActionMapper = intentToActionMapper,
            backgroundDispatcher = backgroundDispatcher,
            bootstrapperDispatcher = bootstrapperDispatcher
        ) {}
    }

}