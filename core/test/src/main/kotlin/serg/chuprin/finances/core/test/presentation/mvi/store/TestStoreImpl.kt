package serg.chuprin.finances.core.test.presentation.mvi.store

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
 *
 * Test store contains the same logic as [BaseStateStore] but it capture all
 * states and events into [capturedStates] and [capturedEvents] accordingly.
 *
 * Also it uses [TestCoroutineDispatcher] for [backgroundDispatcher] and [reducerDispatcher].
 * For time-based actions there is [backgroundTestDispatcher] exposed.
 */
open class TestStoreImpl<I, SE, A, S, E>(
    initialState: S,
    reducer: StoreStateReducer<SE, S>,
    bootstrapper: StoreBootstrapper<A>,
    actionExecutor: StoreActionExecutor<A, S, SE, E>,
    intentToActionMapper: StoreIntentToActionMapper<I, A>
) : BaseStateStore<I, SE, A, S, E>(
    reducer = reducer,
    actionExecutor = actionExecutor,
    initialState = initialState,
    bootstrapper = bootstrapper,
    reducerDispatcher = TestCoroutineDispatcher(),
    bootstrapperDispatcher = TestCoroutineDispatcher(),
    backgroundDispatcher = TestCoroutineDispatcher(),
    intentToActionMapper = intentToActionMapper
), TestStore<I, S, E> {

    override val capturedStates = LinkedList<S>()

    override val capturedEvents = LinkedList<E>()

    override val scope: TestCoroutineScope = TestCoroutineScope(TestCoroutineDispatcher())

    override val backgroundTestDispatcher: TestCoroutineDispatcher =
        backgroundDispatcher as TestCoroutineDispatcher

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