package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreFactory

/**
 * Created by Sergey Chuprin on 03.07.2020.
 */
interface CoreStoreProvider {

    val currencyChoiceStoreFactory: CurrencyChoiceStoreFactory

}