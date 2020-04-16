package serg.chuprin.finances.core.mvi.store.counter

import kotlinx.coroutines.FlowPreview
import serg.chuprin.finances.core.mvi.bootstrapper.BypassStoreBootstrapper
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.core.test.utils.TestStateStore

/**
 * Created by Sergey Chuprin on 17.03.2019.
 */
@FlowPreview
object CounterTestStoreFactory {

    fun build(
        bootstrapper: StoreBootstrapper<CounterTestAction> = BypassStoreBootstrapper()
    ): TestStateStore<CounterTestIntent, CounterTestAction, CounterTestAction, CounterTestState, Nothing> {
        return TestStateStore(
            bootstrapper = bootstrapper,
            reducer = CounterTestReducer(),
            initialState = CounterTestState(),
            executor = CounterTestActionExecutor(),
            stateStoreIntentToActionMapper = CounterTestIntentToActionMapper()
        )
    }

}