package serg.chuprin.finances.core.test.utils

import kotlinx.coroutines.Job
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
 * Created by Sergey Chuprin on 24.09.2019.
 */
class TestStateStore<I, SE, A, S, E>(
    initialState: S,
    reducer: StoreStateReducer<SE, S>,
    bootstrapper: StoreBootstrapper<A>,
    executor: StoreActionExecutor<A, S, SE, E>,
    stateStoreIntentToActionMapper: StoreIntentToActionMapper<I, A>,
    val reducerTestDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
    val backgroundTestDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : BaseStateStore<I, SE, A, S, E>(
    reducer = reducer,
    executor = executor,
    initialState = initialState,
    bootstrapper = bootstrapper,
    reducerDispatcher = reducerTestDispatcher,
    backgroundDispatcher = backgroundTestDispatcher,
    intentToActionMapper = stateStoreIntentToActionMapper
) {

    val capturedStates = LinkedList<S>()
    val capturedEvents = LinkedList<E>()

    val lastEvent: E
        get() = capturedEvents.last

    val scope: TestCoroutineScope = TestCoroutineScope(TestCoroutineDispatcher())

    fun testSubscribe(): Job {
        return scope.launch {
            launch {
                stateFlow().collect { newState ->
                    capturedStates.addLast(newState)
                }
            }
            launch {
                eventsFlow().collect { newEvent ->
                    capturedEvents.addLast(newEvent)
                }
            }
            start(emptyFlow(), this)
        }
    }

}