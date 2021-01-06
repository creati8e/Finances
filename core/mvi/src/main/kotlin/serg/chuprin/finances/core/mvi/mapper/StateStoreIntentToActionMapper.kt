package serg.chuprin.finances.core.mvi.mapper

/**
 * Created by Sergey Chuprin on 09.08.2019.
 */
typealias StoreIntentToActionMapper<I, A> = (intent: I) -> A