package serg.chuprin.finances.core.mvi.store.counter

import serg.chuprin.finances.core.mvi.bootstrapper.BypassStoreBootstrapper
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import serg.chuprin.finances.core.test.factory.TestStoreFactory.Companion.test
import serg.chuprin.finances.core.test.store.TestStore

/**
 * Created by Sergey Chuprin on 17.03.2019.
 */
class CounterTestStoreFactory(
    bootstrapper: StoreBootstrapper<CounterTestAction>
) : AbsStoreFactory<CounterTestIntent, CounterTestAction, CounterTestAction, CounterTestState, Nothing, CounterTestStore>(
    bootstrapper = bootstrapper,
    reducer = CounterTestReducer(),
    initialState = CounterTestState(),
    actionExecutor = CounterTestActionExecutor(),
    intentToActionMapper = CounterTestIntentToActionMapper()
) {

    companion object {

        fun build(
            bootstrapper: StoreBootstrapper<CounterTestAction> = BypassStoreBootstrapper()
        ): TestStore<CounterTestIntent, CounterTestState, Nothing> {
            return CounterTestStoreFactory(bootstrapper).test()
        }

    }

}