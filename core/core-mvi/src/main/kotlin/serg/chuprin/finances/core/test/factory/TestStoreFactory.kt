package serg.chuprin.finances.core.test.factory

import serg.chuprin.finances.core.mvi.store.StateStore
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import serg.chuprin.finances.core.test.store.TestStore
import serg.chuprin.finances.core.test.store.TestStoreImpl

/**
 * Created by Sergey Chuprin on 08.12.2020.
 */
class TestStoreFactory<I, SE, A, S, E, STORE : StateStore<I, S, E>>(
    private val delegate: AbsStoreFactory<I, SE, A, S, E, STORE>
) : AbsStoreFactory<I, SE, A, S, E, TestStore<I, S, E>>(
    reducer = delegate.reducer,
    executor = delegate.executor,
    initialState = delegate.initialState,
    bootstrapper = delegate.bootstrapper,
    intentToActionMapper = delegate.intentToActionMapper
) {

    companion object {

        fun <I, SE, A, S, E, STORE : StateStore<I, S, E>> AbsStoreFactory<I, SE, A, S, E, STORE>.test(): TestStore<I, S, E> {
            return TestStoreFactory(this).create()
        }

    }

    override fun create(): TestStore<I, S, E> {
        return object : TestStoreImpl<I, SE, A, S, E>(
            reducer = delegate.reducer,
            executor = delegate.executor,
            initialState = delegate.initialState,
            bootstrapper = delegate.bootstrapper,
            intentToActionMapper = delegate.intentToActionMapper
        ) {}
    }

}