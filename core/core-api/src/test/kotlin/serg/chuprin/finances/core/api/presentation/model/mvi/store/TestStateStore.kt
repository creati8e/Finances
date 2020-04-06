package serg.chuprin.finances.core.api.presentation.model.mvi.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import serg.chuprin.finances.core.api.presentation.model.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.core.api.presentation.model.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.api.presentation.model.mvi.mapper.StoreIntentToActionMapper
import serg.chuprin.finances.core.api.presentation.model.mvi.reducer.StoreStateReducer
import java.util.*

/**
 * Created by Sergey Chuprin on 24.09.2019.
 */
@FlowPreview
class TestStateStore<I, SE, A, S, E>(
    initialState: S,
    reducer: StoreStateReducer<SE, S>,
    bootstrapper: StoreBootstrapper<A>,
    executor: StoreActionExecutor<A, S, SE, E>,
    stateStoreIntentToActionMapper: StoreIntentToActionMapper<I, A>
) : BaseStateStore<I, SE, A, S, E>(
    reducer = reducer,
    executor = executor,
    initialState = initialState,
    bootstrapper = bootstrapper,
    reducerDispatcher = TestCoroutineDispatcher(),
    backgroundDispatcher = TestCoroutineDispatcher(),
    intentToActionMapper = stateStoreIntentToActionMapper
) {

    val capturedStates = LinkedList<S>()
    val capturedEvents = LinkedList<E>()

    val lastEvent: E
        get() = capturedEvents.last

    val scope: CoroutineScope = TestCoroutineScope(TestCoroutineDispatcher())

    fun testSubscribe(): Job {
        return scope.launch {
            launch {
                observeState().collect { newState ->
                    capturedStates.addLast(newState)
                }
            }
            launch {
                observeEvents().collect { newEvent ->
                    capturedEvents.addLast(newEvent)
                }
            }
            start(emptyFlow(), scope)
        }
    }

}