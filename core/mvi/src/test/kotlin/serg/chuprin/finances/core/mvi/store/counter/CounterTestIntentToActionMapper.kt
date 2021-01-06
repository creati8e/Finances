package serg.chuprin.finances.core.mvi.store.counter

import serg.chuprin.finances.core.mvi.mapper.StoreIntentToActionMapper

/**
 * Created by Sergey Chuprin on 23.09.2019.
 */
class CounterTestIntentToActionMapper :
    StoreIntentToActionMapper<CounterTestIntent, CounterTestAction> {

    override fun invoke(intent: CounterTestIntent): CounterTestAction {
        return CounterTestAction.Execute(intent)
    }

}