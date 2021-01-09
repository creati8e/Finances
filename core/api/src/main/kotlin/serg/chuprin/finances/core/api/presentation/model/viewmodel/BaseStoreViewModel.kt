package serg.chuprin.finances.core.api.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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

    protected fun intentsFlow(): Flow<INTENT> = intentsChannel.asFlow()

    protected fun <E, STORE : StateStore<*, *, E>> STORE.observeEventsAsLiveData(): LiveData<E> {
        return eventsFlow.asSingleEventLiveData()
    }

    protected inline fun <E, STORE : StateStore<*, *, E>, reified T> STORE.observeTypedEventsAsLiveData(): LiveData<T> {
        return eventsFlow.filterIsInstance<T>().asSingleEventLiveData()
    }

    protected fun <STATE, STORE : StateStore<*, STATE, *>, PROPERTY> STORE.observeParticularStateAsLiveData(
        picker: (STATE) -> PROPERTY
    ): LiveData<PROPERTY> {
        return stateFlow
            .map { state -> picker(state) }
            .distinctUntilChanged()
            .asLiveData()
    }

    protected fun <STATE, STORE : StateStore<*, STATE, *>> STORE.observeStateAsLiveData(): LiveData<STATE> {
        return stateFlow
            .distinctUntilChanged()
            .asLiveData()
    }

    protected fun <T> Flow<T>.asLiveData(): LiveData<T> {
        return asLiveData(viewModelScope.coroutineContext)
    }

    protected fun <T> Flow<T>.asSingleEventLiveData(): SingleEventLiveData<T> {
        return SingleEventLiveData<T>().apply {
            viewModelScope.launch {
                collect { event ->
                    value = event
                }
            }
        }
    }

}