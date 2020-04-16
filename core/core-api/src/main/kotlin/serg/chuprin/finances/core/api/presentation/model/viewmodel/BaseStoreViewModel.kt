package serg.chuprin.finances.core.api.presentation.model.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import serg.chuprin.finances.core.api.presentation.model.SingleEventLiveData
import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 07.04.2020.
 */
abstract class BaseStoreViewModel<INTENT> : ViewModel() {

    private val intentsChannel = BroadcastChannel<INTENT>(Channel.BUFFERED)

    fun dispatchIntent(intent: INTENT) {
        intentsChannel.offer(intent)
    }

    protected fun <T> Flow<T>.asLiveData(): LiveData<T> {
        return asLiveData(viewModelScope.coroutineContext)
    }

    protected fun <E, STORE : StateStore<*, *, E>> STORE.observeEventsAsLiveData(): LiveData<E> {
        return SingleEventLiveData<E>().apply {
            viewModelScope.launch {
                eventsFlow().collect { event ->
                    value = event
                }
            }
        }
    }

    protected inline fun <E, STORE : StateStore<*, *, E>, reified T> STORE.observeTypedEventsAsLiveData(): LiveData<T> {
        return SingleEventLiveData<T>().apply {
            viewModelScope.launch {
                eventsFlow()
                    .filter { it is T }
                    .collect { event ->
                        value = event as T
                    }
            }
        }
    }

    protected fun <STATE, STORE : StateStore<*, STATE, *>, PROPERTY> STORE.observeParticularStateAsLiveData(
        picker: (STATE) -> PROPERTY
    ): LiveData<PROPERTY> {
        return liveData {
            stateFlow()
                .map { state -> picker(state) }
                .distinctUntilChanged()
                .collect { property ->
                    emit(property)
                }
        }
    }

    protected fun <STATE, STORE : StateStore<*, STATE, *>> STORE.observeStateAsLiveData(): LiveData<STATE> {
        return liveData {
            stateFlow()
                .distinctUntilChanged()
                .collect { state ->
                    emit(state)
                }
        }
    }

    @OptIn(FlowPreview::class)
    protected fun intentsFlow(): Flow<INTENT> = intentsChannel.asFlow()

}