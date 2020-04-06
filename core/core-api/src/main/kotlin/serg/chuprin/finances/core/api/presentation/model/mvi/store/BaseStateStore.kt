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
 * 5. New state is produced and [observeState] emits new state.
 *
 * This implementation automatically executes action on background thread.
 */
@FlowPreview
@Suppress("MemberVisibilityCanBePrivate")
open class BaseStateStore<I, SE, A, S, E>(
    initialState: S,
    protected val reducer: StoreStateReducer<SE, S>,
    protected val bootstrapper: StoreBootstrapper<A>,
    protected val executor: StoreActionExecutor<A, S, SE, E>,
    protected val intentToActionMapper: StoreIntentToActionMapper<I, A>,
    protected val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default,
    protected val reducerDispatcher: CoroutineDispatcher = newSingleThreadContext("reducer")
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

    override fun start(intents: Flow<I>, scope: CoroutineScope): Job {
        require(!isStarted) {
            "Store is started already"
        }
        isStarted = true
        return scope.launch {
            launch(CoroutineName("Intent dispatcher coroutine")) {
                intents.collect { intent ->
                    coroutineContext.ensureActive()
                    dispatch(intent)
                }
            }

            launch(CoroutineName("Effect reducer coroutine")) {
                actionsChannel
                    .asFlow()
                    .flatMapConcat { action ->
                        executor(action, state, eventsChannel.asConsumer())
                    }
                    // Execute all actions on background thread.
                    .flowOn(backgroundDispatcher)
                    .map { sideEffect ->
                        reducer(sideEffect, state)
                    }
                    // Reduce on single thread to prevent interleaving.
                    .flowOn(reducerDispatcher)
                    .distinctUntilChanged()
                    .collect { newState ->
                        coroutineContext.ensureActive()
                        statesChannel.sendBlocking(newState)
                    }
            }

            launch(CoroutineName("Bootstrapper coroutine")) {
                bootstrapper()
                    .flowOn(backgroundDispatcher)
                    .collect { action ->
                        coroutineContext.ensureActive()
                        actionsChannel.sendBlocking(action)
                    }
            }
        }
    }

    override fun observeState(): Flow<S> = statesChannel.asFlow()

    override fun observeEvents(): Flow<E> = eventsChannel.asFlow()

    override fun dispatch(intent: I) = actionsChannel.sendBlocking(intentToActionMapper(intent))

    protected suspend fun dispatchAction(action: A) = actionsChannel.send(action)

    private fun <T> SendChannel<T>.asConsumer(): Consumer<T> {
        return Consumer { value ->
            sendBlocking(value)
        }
    }

}