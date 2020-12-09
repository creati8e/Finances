package serg.chuprin.finances.core.test.store

import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestCoroutineScope
import serg.chuprin.finances.core.mvi.store.BaseStore
import java.util.*

/**
 * Created by Sergey Chuprin on 08.12.2020.
 */
interface TestStore<I, S, E> : BaseStore<I, S, E> {

    val scope: TestCoroutineScope

    val capturedStates: LinkedList<S>

    val capturedEvents: LinkedList<E>

    val lastEvent: E
        get() = capturedEvents.last

    fun start(): Job

}