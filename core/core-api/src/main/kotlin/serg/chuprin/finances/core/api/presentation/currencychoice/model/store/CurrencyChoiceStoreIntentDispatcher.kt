package serg.chuprin.finances.core.api.presentation.currencychoice.model.store

/**
 * Created by Sergey Chuprin on 04.07.2020.
 */
interface CurrencyChoiceStoreIntentDispatcher {

    fun dispatch(intent: CurrencyChoiceIntent)

}