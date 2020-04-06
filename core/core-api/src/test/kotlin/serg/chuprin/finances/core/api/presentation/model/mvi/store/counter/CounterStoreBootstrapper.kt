package serg.chuprin.finances.core.api.presentation.model.mvi.store.counter

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import serg.chuprin.finances.core.api.presentation.model.mvi.bootstrapper.StoreBootstrapper

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
@FlowPreview
class CounterStoreBootstrapper(
    private val bootstrappedValue: Int
) : StoreBootstrapper<CounterTestAction> {

    private val publishChannel = BroadcastChannel<Int>(Channel.BUFFERED)

    override fun invoke(): Flow<CounterTestAction> {
        return publishChannel
            .asFlow()
            .onStart {
                emit(bootstrappedValue)
            }
            .map { value -> CounterTestAction.UpdateInitialCounter(value) }
    }

    fun simulateBootstrapperUpdate(value: Int) = publishChannel.sendBlocking(value)

}