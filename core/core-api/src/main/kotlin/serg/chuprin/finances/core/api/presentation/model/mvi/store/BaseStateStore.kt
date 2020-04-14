package serg.chuprin.finances.core.api.presentation.model.mvi.store

import androidx.core.util.Consumer
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.presentation.model.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.core.api.presentation.model.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.api.presentation.model.mvi.mapper.StoreIntentToActionMapper
import serg.chuprin.finances.core.api.presentation.model.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 14.07.2019.
 *
 * [I] - type of intent, sent by user.
 * [SE] - type of effect, produced by [executor].
 * [A] - type of action, converted from [I].
 * [S] - type of state, produced by loop.
 *
 * Flow is the following:
 * 1. Intent of type [I] dispatched to store using [dispatch] method.
 * 2. This intent converted to internal action of type [A] using [intentToActionMapper].
 * 3. This action is processed by [executor]. As a result, an effect of type [SE] is produced.
 * 4. This effect processed by [reducer].
 * 5. New state is produced and [stateFlow] emits new state.
 *
 * This implementation automatically executes action on background thread.
 */
@OptIn(FlowPreview::class)
@Suppress("MemberVisibilityCanBePrivate", "unused")
open class BaseStateStore<I, SE, A, S, E>(
    protected val initialState: S,
    protected val reducer: StoreStateReducer<SE, S>,
    protected val bootstrapper: StoreBootstrapper<A>,
    protected val executor: StoreActionExecutor<A, S, SE, E>,
    protected val intentToActionMapper: StoreIntentToActionMapper<I, A>,
    protected val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default,
    protected val reducerDispatcher: CoroutineDispatcher = newSingleThreadContext("Reducer thread")
) : StateStore<I, S, E> {

    override val state: S
        get() = statesChannel.value!!

    /**
     * Subject which publish actions.
     * Serialization is required to guarantee thread-safe actions publishing.
     */
    protected val actionsChannel = BroadcastChannel<A>(Channel.BUFFERED)
    protected val statesChannel = ConflatedBroadcastChannel(initialState)

    private val eventsChannel = BroadcastChannel<E>(Channel.BUFFERED)

    private var isStarted = false

    override fun accept(t: I) = dispatch(t)

    override fun start(intentsFlow: Flow<I>, scope: CoroutineScope) {
        require(!isStarted) {
            "Store is started already"
        }
        isStarted = true
        scope.launch {
            processIntents(this, intentsFlow)
            processActions(this)
            bootstrap(this)
        }
    }

    override fun stateFlow(): Flow<S> = statesChannel.asFlow()

    override fun eventsFlow(): Flow<E> = eventsChannel.asFlow()

    override fun dispatch(intent: I) = actionsChannel.sendBlocking(intentToActionMapper(intent))

    protected suspend fun dispatchAction(action: A) = actionsChannel.send(action)

    private suspend fun processIntents(coroutineScope: CoroutineScope, intentsFlow: Flow<I>) {
        coroutineScope.launch(CoroutineName("Intent dispatcher coroutine")) {
            intentsFlow.collect { intent ->
                ensureActive()
                actionsChannel.send(intentToActionMapper(intent))
            }
        }
    }

    private suspend fun bootstrap(coroutineScope: CoroutineScope) {
        coroutineScope.launch(CoroutineName("Bootstrapper coroutine")) {
            bootstrapper()
                .flowOn(backgroundDispatcher)
                .collect { action ->
                    ensureActive()
                    actionsChannel.send(action)
                }
        }
    }

    private suspend fun processActions(coroutineScope: CoroutineScope) {
        coroutineScope.launch(CoroutineName("Effect reducer coroutine")) {
            actionsChannel.asFlow()
                .flatMapMerge { action ->
                    executor(
                        action,
                        state,
                        eventsChannel.asConsumer(),
                        actionsChannel.asFlow()
                    )
                }
                // Execute all actions on background thread.
                .flowOn(backgroundDispatcher)
                .scan(initialState) { prevState, sideEffect ->
                    reducer(sideEffect, prevState)
                }
                // Reduce on single thread to prevent interleaving.
                .flowOn(reducerDispatcher)
                .collect { newState ->
                    ensureActive()
                    statesChannel.send(newState)
                }
        }
    }

    private fun <T> SendChannel<T>.asConsumer(): Consumer<T> {
        return Consumer { value ->
            sendBlocking(value)
        }
    }

}