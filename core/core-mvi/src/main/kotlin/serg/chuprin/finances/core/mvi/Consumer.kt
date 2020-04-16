package serg.chuprin.finances.core.mvi

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
interface Consumer<T> {
    fun accept(value: T)
}

operator fun <T> Consumer<T>.invoke(event: T) = accept(event)