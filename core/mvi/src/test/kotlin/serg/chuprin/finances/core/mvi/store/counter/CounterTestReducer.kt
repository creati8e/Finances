package serg.chuprin.finances.core.mvi.store.counter

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 17.03.2019.
 */
class CounterTestReducer : StoreStateReducer<CounterTestAction, CounterTestState> {

    override fun invoke(
        action: CounterTestAction,
        state: CounterTestState
    ): CounterTestState {
        return when (action) {
            is CounterTestAction.Execute -> {
                when (action.intent) {
                    CounterTestIntent.Increment -> state.copy(counter = state.counter + 1)
                    CounterTestIntent.Decrement -> state.copy(counter = state.counter - 1)
                }
            }
            is CounterTestAction.UpdateInitialCounter -> state.copy(counter = action.value)
        }
    }

}