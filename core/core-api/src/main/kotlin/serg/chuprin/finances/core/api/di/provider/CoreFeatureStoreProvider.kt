package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
interface CoreFeatureStoreProvider {

    val currencyChoiceStore: CurrencyChoiceStore

}