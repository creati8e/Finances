package serg.chuprin.finances.core.test.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.mapper.StoreIntentToActionMapper
import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import java.util.*

/**
 * Created by Sergey Chuprin on 08.12.2020.
 */
open class TestStoreImpl<I, SE, A, S, E>(
    initialState: S,
    reducer: StoreStateReducer<SE, S>,
    bootstrapper: StoreBootstrapper<A>,
    executor: StoreActionExecutor<A, S, SE, E>,
    intentToActionMapper: StoreIntentToActionMapper<I, A>
) : BaseStateStore<I, SE, A, S, E>(
    reducer = reducer,
    executor = executor,
    initialState = initialState,
    bootstrapper = bootstrapper,
    reducerDispatcher = TestCoroutineDispatcher(),
    backgroundDispatcher = TestCoroutineDispatcher(),
    intentToActionMapper = intentToActionMapper
), TestStore<I, S, E> {

    override val capturedStates = LinkedList<S>()

    override val capturedEvents = LinkedList<E>()

    override val scope: TestCoroutineScope = TestCoroutineScope(TestCoroutineDispatcher())

    override fun start(): Job {
        return start(emptyFlow(), scope)
    }

    override fun start(intentsFlow: Flow<I>, scope: CoroutineScope): Job {
        return scope.launch {
            launch {
                stateFlow.collect { newState ->
                    capturedStates.addLast(newState)
                }
            }
            launch {
                eventsFlow.collect { newEvent ->
                    capturedEvents.addLast(newEvent)
                }
            }
            super.start(intentsFlow, this)
        }
    }

}