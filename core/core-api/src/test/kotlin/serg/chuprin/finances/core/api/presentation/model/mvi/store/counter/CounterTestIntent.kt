package serg.chuprin.finances.core.api.presentation.model.mvi.store.counter

/**
 * Created by Sergey Chuprin on 23.09.2019.
 */
sealed class CounterTestIntent {

    object Increment : CounterTestIntent()

    object Decrement : CounterTestIntent()

}