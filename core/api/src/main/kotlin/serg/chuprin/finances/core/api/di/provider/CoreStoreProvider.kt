package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreProvider

/**
 * Created by Sergey Chuprin on 03.07.2020.
 */
interface CoreStoreProvider {

    val currencyChoiceStoreProvider: CurrencyChoiceStoreProvider

}