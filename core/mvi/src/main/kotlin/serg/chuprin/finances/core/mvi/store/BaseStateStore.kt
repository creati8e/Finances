package serg.chuprin.finances.core.mvi.store

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.mapper.StoreIntentToActionMapper
import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 14.07.2019.
 *
 * [I] - type of intent, sent by user.
 * [SE] - type of effect, produced by [actionExecutor].
 * [A] - type of action, converted from [I].
 * [S] - type of state, produced by loop.
 *
 * Flow is the following:
 * 1. Intent of type [I] dispatched to store using [dispatch] method.
 * 2. This intent converted to internal action of type [A] using [intentToActionMapper].
 * 3. This action is processed by [actionExecutor]. As a result, an effect of type [SE] is produced.
 * 4. This effect processed by [reducer].
 * 5. New state is produced and [stateFlow] emits new state.
 *
 * This implementation automatically executes action on background thread.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
open class BaseStateStore<I, SE, A, S, E>(
    protected val initialState: S,
    protected val reducer: StoreStateReducer<SE, S>,
    protected val bootstrapper: StoreBootstrapper<A>,
    protected val actionExecutor: StoreActionExecutor<A, S, SE, E>,
    protected val intentToActionMapper: StoreIntentToActionMapper<I, A>,
    protected val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default,
    protected val reducerDispatcher: CoroutineDispatcher =
        newSingleThreadContext("Reducer coroutine context"),
    protected val bootstrapperDispatcher: CoroutineDispatcher =
        newSingleThreadContext("Bootstrapper coroutine context")
) : StateStore<I, S, E> {

    override val state: S
        get() = statesChannel.value!!

    /**
     * Subject which publish actions.
     * Serialization is required to guarantee thread-safe actions publishing.
     */
    protected val actionsChannel = BroadcastChannel<A>(Channel.BUFFERED)
    protected val statesChannel = MutableStateFlow(initialState)

    private val eventsChannel = BroadcastChannel<E>(Channel.BUFFERED)

    private var isStarted = false

    override fun start(intentsFlow: Flow<I>, scope: CoroutineScope): Job {
        require(!isStarted) {
            "Store is started already"
        }
        isStarted = true
        return scope.launch {
            processIntents(this, intentsFlow)
            processActions(this)
            bootstrap(this)
        }
    }

    override val stateFlow: Flow<S>
        get() = statesChannel

    override val eventsFlow: Flow<E>
        get() = eventsChannel.asFlow()

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
                .flowOn(bootstrapperDispatcher)
                .collect { action ->
                    ensureActive()
                    actionsChannel.send(action)
                }
        }
    }

    private suspend fun processActions(coroutineScope: CoroutineScope) {
        coroutineScope.launch(CoroutineName("Effect reducer coroutine")) {
            val actionsFlow = actionsChannel.asFlow()
            val eventConsumer = eventsChannel.asConsumer()
            actionsFlow
                .flatMapMerge { action ->
                    actionExecutor(
                        action,
                        state,
                        eventConsumer,
                        actionsFlow
                    )
                }
                // Execute all actions on background thread.
                .flowOn(backgroundDispatcher)
                .scan(initialState) { prevState, sideEffect ->
                    reducer(sideEffect, prevState)
                }
                // Initial action is emitted twice due to scan operator. Skip first emission.
                .drop(1)
                // Reduce on single thread to prevent interleaving.
                .flowOn(reducerDispatcher)
                .collect { newState ->
                    ensureActive()
                    statesChannel.value = newState
                }
        }
    }

    private fun <T> SendChannel<T>.asConsumer(): Consumer<T> {
        return object : Consumer<T> {
            override fun accept(value: T) {
                sendBlocking(value)
            }
        }
    }

}