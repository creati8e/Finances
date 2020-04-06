package serg.chuprin.finances.core.api.presentation.model.mvi.mapper

/**
 * Created by Sergey Chuprin on 09.08.2019.
 */
typealias StoreIntentToActionMapper<I, A> = (I) -> A