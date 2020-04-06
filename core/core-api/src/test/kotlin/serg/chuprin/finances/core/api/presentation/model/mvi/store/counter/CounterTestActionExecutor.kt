package serg.chuprin.finances.core.api.presentation.model.mvi.store.counter

import androidx.core.util.Consumer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.presentation.model.mvi.executor.StoreActionExecutor

/**
 * Created by Sergey Chuprin on 23.09.2019.
 */
class CounterTestActionExecutor :
    StoreActionExecutor<CounterTestAction, CounterTestState, CounterTestAction, Nothing> {

    override fun invoke(
        action: CounterTestAction,
        state: CounterTestState,
        eventConsumer: Consumer<Nothing>
    ): Flow<CounterTestAction> {
        return flowOf(action)
    }

}