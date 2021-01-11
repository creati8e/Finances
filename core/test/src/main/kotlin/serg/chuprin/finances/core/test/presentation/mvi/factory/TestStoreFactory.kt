package serg.chuprin.finances.core.test.presentation.mvi.factory

import serg.chuprin.finances.core.mvi.store.StateStore
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import serg.chuprin.finances.core.test.presentation.mvi.store.TestStore
import serg.chuprin.finances.core.test.presentation.mvi.store.TestStoreImpl

/**
 * Created by Sergey Chuprin on 08.12.2020.
 *
 * Factory that creates [TestStore] which exposes only [TestStore.start] method without parameters.
 */
class TestStoreFactory<I, SE, A, S, E, STORE : StateStore<I, S, E>>(
    private val delegate: AbsStoreFactory<I, SE, A, S, E, STORE>
) : AbsStoreFactory<I, SE, A, S, E, TestStore<I, S, E>>(
    reducer = delegate.reducer,
    actionExecutor = delegate.actionExecutor,
    initialState = delegate.initialState,
    bootstrapper = delegate.bootstrapper,
    intentToActionMapper = delegate.intentToActionMapper
) {

    companion object {

        /**
         * Wraps existing store factory with [TestStoreFactory] and creates [TestStore].
         *
         *  val testStore = MyStoreFactory(
         *       executor = MyStoreExecutorImpl(),
         *       bootstrapper = MyStoreBootstrapperImpl()
         *   ).test()
         */
        fun <I, SE, A, S, E, STORE : StateStore<I, S, E>> AbsStoreFactory<I, SE, A, S, E, STORE>.test(): TestStore<I, S, E> {
            return TestStoreFactory(this).create()
        }

    }

    override fun create(): TestStore<I, S, E> {
        return object : TestStoreImpl<I, SE, A, S, E>(
            reducer = delegate.reducer,
            actionExecutor = delegate.actionExecutor,
            initialState = delegate.initialState,
            bootstrapper = delegate.bootstrapper,
            intentToActionMapper = delegate.intentToActionMapper
        ) {}
    }

}