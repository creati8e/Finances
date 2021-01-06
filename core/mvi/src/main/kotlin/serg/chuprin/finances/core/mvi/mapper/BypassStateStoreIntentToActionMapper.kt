package serg.chuprin.finances.core.mvi.mapper

/**
 * Created by Sergey Chuprin on 09.08.2019.
 */
class BypassStateStoreIntentToActionMapper<I : Any> : StoreIntentToActionMapper<I, I> {

    override fun invoke(intent: I): I = intent

}