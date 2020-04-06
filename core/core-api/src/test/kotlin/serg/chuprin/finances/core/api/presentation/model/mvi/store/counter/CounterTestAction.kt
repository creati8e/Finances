package serg.chuprin.finances.core.api.presentation.model.mvi.store.counter

/**
 * Created by Sergey Chuprin on 23.09.2019.
 */
sealed class CounterTestAction {

    data class UpdateInitialCounter(
        val value: Int
    ) : CounterTestAction()

    data class Execute(
        val intent: CounterTestIntent
    ) : CounterTestAction()

}