package serg.chuprin.finances.core.mvi.store.counter

import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 08.12.2020.
 */
interface CounterTestStore : StateStore<CounterTestIntent, CounterTestState, Nothing>