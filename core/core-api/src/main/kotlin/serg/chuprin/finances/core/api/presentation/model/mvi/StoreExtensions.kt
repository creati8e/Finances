package serg.chuprin.finances.core.api.presentation.model.mvi

import androidx.core.util.Consumer

/**
 * Created by Sergey Chuprin on 18.10.2019.
 */
operator fun <T> Consumer<T>.invoke(event: T) = accept(event)