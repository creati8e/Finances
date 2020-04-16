package serg.chuprin.finances.core.mvi.store.counter

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor

/**
 * Created by Sergey Chuprin on 23.09.2019.
 */
class CounterTestActionExecutor :
    StoreActionExecutor<CounterTestAction, CounterTestState, CounterTestAction, Nothing> {

    override fun invoke(
        action: CounterTestAction,
        state: CounterTestState,
        eventConsumer: Consumer<Nothing>,
        actionsFlow: Flow<CounterTestAction>
    ): Flow<CounterTestAction> {
        return flowOf(action)
    }

}